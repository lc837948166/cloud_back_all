package com.xw.cloud.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xw.cloud.Utils.CommonResp;
import com.xw.cloud.Utils.ProcessUtils;

import com.xw.cloud.Utils.TaskUtils;
import com.xw.cloud.bean.ConstructionInfo;
import com.xw.cloud.bean.NodeInfo;
import com.xw.cloud.bean.Task;
import com.xw.cloud.bean.VMInfo2;
import com.xw.cloud.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.util.*;


@CrossOrigin
@Controller
public class ProcessController {

    @Autowired
    private ConstructionService constructionService;
    @Autowired
    private VmService vmService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private LibvirtService libvirtService;
    private final String sufixUrl = ":8081/api/ssh/execute2";

    @Autowired
    private TaskService taskService;



    @RequestMapping(value = "/addVirtual", method = RequestMethod.POST)
    @ApiOperation(value = "创建虚拟机接口", notes = "创建虚拟机并上传镜像")
    @ResponseBody
    public void addVirtual() throws JsonProcessingException {
        ProcessUtils processUtils = new ProcessUtils();
        List<Task> tasks = taskService.list();
        for(Task task: tasks){
            //记录中间任务执行失败情况
            String task_attributes_values = task.getTASK_ATTRIBUTES_VALUES();
            ObjectMapper mapper = new ObjectMapper();
            TaskUtils taskUtils = null;
            try {
                taskUtils = mapper.readValue(task_attributes_values, TaskUtils.class);
            } catch (IOException e) {
                task.setSTATUS(5);
                taskUtils.setTask_status(5);
                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                taskService.updateById(task);
                continue;
            }
            System.out.println(taskUtils);
            if((taskUtils.getTask_status() == 1 || taskUtils.getTask_status() == 5) && (taskUtils.getTask_executor() == 1 || taskUtils.getTask_executor() == 3) && taskUtils.getExecution_method().contains("addVirtual")){
                System.out.println("开始执行任务");
                boolean flag = false;
                int vmNum = taskUtils.getVm_num();
                String mkcommand = "mkdir -p /etc/usr/xwfiles";
                if(vmNum <= 0) {
                    task.setSTATUS(5);
                    taskUtils.setTask_status(5);
                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                    taskService.updateById(task);
                    continue;
                }
                String vm_image_name = taskUtils.getVm_image_name();
                String vm_name = taskUtils.getVm_name();
                Integer memory = taskUtils.getMemory();
                Integer cpu_num = taskUtils.getCpu_npm();
                String docker_image_name = taskUtils.getDocker_image_name();
                String[] com = taskUtils.getCmds().split(",");
                if(vm_image_name==null || vm_name == null || memory== null || cpu_num == null || docker_image_name == null||com == null) {
                    task.setSTATUS(5);
                    taskUtils.setTask_status(5);
                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                    taskService.updateById(task);
                    continue;
                }

                String OStype = "X86";
                String nettype = "bridge";

                Integer is_all_pm = taskUtils.getIs_all_pm();
                List<NodeInfo>  nodes = null;
                if(is_all_pm == 1){
                    nodes = nodeService.list(new QueryWrapper<NodeInfo>().eq("nodeType", "端"));
                }
                String ips = "";
                if(nodes == null){
                    String Pmip = taskUtils.getPm_ip();
                    String url = "http://" + Pmip + sufixUrl;
                    task.setSTATUS(2);
                    taskUtils.setTask_status(2);
                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                    taskService.updateById(task);

                    System.out.println("云节点开始下发Docker镜像");
                    //下发镜像文件
                    String ans = null;
                    try {
                        ans = processUtils.dispenseImgByIP("39.98.124.97", docker_image_name, Pmip);
                    } catch (Exception e) {
                        e.printStackTrace();
                        task.setSTATUS(5);
                        taskUtils.setTask_status(5);
                        task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                        flag = true;
                        taskService.updateById(task);
                        System.out.println("下发失败");
                        continue;
                    }
                    if (ans.contains("200")) {
                        if (ans.contains("-1")) {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            System.out.println("下发失败1");
                            continue;
                        }
                    } else {
                        task.setSTATUS(5);
                        taskUtils.setTask_status(5);
                        task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                        flag = true;
                        taskService.updateById(task);
                        System.out.println("下发失败2");
                        continue;
                    }

                    //循环上传镜像
                    for(int i = 1; i <= vmNum;i++) {
                        String vmi_name = vm_name+"_"+i;
                        System.out.println("开始创建第"+i+"个虚拟机");
                        //创建虚拟机
                        try {
                            ans = processUtils.createVM(vm_image_name,vmi_name,memory,cpu_num,OStype,nettype,Pmip);
                            if (ans.contains("200")) {
                                if (ans.contains("重复")) {
                                    task.setSTATUS(5);
                                    taskUtils.setTask_status(5);
                                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                    flag = true;
                                    taskService.updateById(task);
                                    break;
                                }else {
                                    QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
                                    qw1.eq("name", vmi_name);
                                    VMInfo2 vm = vmService.getOne(qw1);
                                    if (vm.getIp() == null) {
                                        boolean ff = false;
                                        for (int j = 0; j < 8; ++j) {
                                            if (vmService.getOne(qw1).getIp() == null || vmService.getOne(qw1).getIp().isEmpty()) {
                                                Thread.sleep(6000);
                                                libvirtService.getallVMip(Pmip);
                                            } else {
                                                ff = true;
                                                System.out.println("第"+i+"个虚拟机获得IP:"+vmService.getOne(qw1).getIp());
                                                if(ips.equals("")){
                                                    ips = vmService.getOne(qw1).getIp();
                                                }else
                                                {
                                                    ips +=","+vmService.getOne(qw1).getIp();
                                                }
                                                break;
                                            }
                                        }
                                        if(!ff) {
                                            task.setSTATUS(5);
                                            taskUtils.setTask_status(5);
                                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                            flag = true;
                                            taskService.updateById(task);
                                            break;
                                        }
                                    }else {
                                        System.out.println("第"+i+"个虚拟机获得IP:"+vmService.getOne(qw1).getIp());
                                        if(ips.equals("")){
                                            ips = vmService.getOne(qw1).getIp();
                                        }else
                                        {
                                            ips +=","+vmService.getOne(qw1).getIp();
                                        }
                                    }
                                }
                            } else {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }
                        } catch (Exception e) {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }

                        System.out.println("第"+i+"个虚拟机执行命令创建文件夹");
                        //执行命令创建文件夹
                        QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
                        qw1.eq("name", vmi_name);
                        VMInfo2 vm = vmService.getOne(qw1);
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        // 构建请求体
                        Map<String, Object> requestBody = new HashMap<>();
                        requestBody.put("host", vm.getIp());
                        requestBody.put("username", vm.getUsername());
                        requestBody.put("password", vm.getPasswd());
                        List<String> commands = Arrays.asList(
                                mkcommand
                        );
                        requestBody.put("commands", commands);
                        System.out.println(commands);
                        // 发起请求
                        ResponseEntity<String> response = new RestTemplate().exchange(
                                url,
                                HttpMethod.POST,
                                new HttpEntity<>(requestBody, headers),
                                String.class
                        );
                        if (!response.toString().contains("\"exitStatus\":0")) {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }
                        //上传文件到虚拟机
                        System.out.println("第"+i+"个虚拟机上传文件到虚拟机");
                        try {
                            ans = processUtils.uploadDockerToVM(docker_image_name, vmi_name, Pmip, "39.98.124.97");
                        } catch (Exception e) {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }
                        System.out.println(ans);
                        if (ans.contains("200")) {
                            if (ans.contains("-1")) {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }
                        } else {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }

                        System.out.println("第"+i+"个虚拟机导入镜像");
                        //导入镜像
                        try {
                            ans = processUtils.importImage(docker_image_name, vmi_name, Pmip);
                        } catch (Exception e) {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }
                        System.out.println(ans);
                        if (ans.contains("200")) {
                            if (ans.contains("-1")) {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }
                        } else {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }
                        System.out.println("第"+i+"个虚拟机执行命令运行镜像");
                        //执行命令运行镜像
                        HttpHeaders headers1 = new HttpHeaders();
                        headers1.setContentType(MediaType.APPLICATION_JSON);

                        // 构建请求体
                        Map<String, Object> requestBody1 = new HashMap<>();
                        requestBody1.put("host", vm.getIp());
                        requestBody1.put("username", vm.getUsername());
                        requestBody1.put("password", vm.getPasswd());
                        List<String> commands1 = Arrays.asList(
                                com
                        );
                        requestBody1.put("commands", commands1);
                        System.out.println(commands1);
                        // 发起请求
                        ResponseEntity<String> response1 = new RestTemplate().exchange(
                                url,
                                HttpMethod.POST,
                                new HttpEntity<>(requestBody1, headers1),
                                String.class
                        );
                        if (!response1.toString().contains("\"exitStatus\":0")) {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }
                    }
                    if(flag == false){
                        task.setSTATUS(4);
                        taskUtils.setTask_status(4);
                        taskUtils.setVm_ip(ips);
                        task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                        taskService.updateById(task);
                    }
                }else {
                    task.setSTATUS(2);
                    taskUtils.setTask_status(2);
                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                    taskService.updateById(task);
                    int cnt = 0;
                    for(NodeInfo node : nodes){
                        String Pmip = node.getNodeIp();
                        String url = "http://" + Pmip + sufixUrl;
                        //循环上传镜像
                        if(flag)
                            break;
                        //下发镜像文件
                        System.out.println("节点"+node.getNodeName()+"下发镜像文件");
                        String ans = null;
                        try {
                            ans = processUtils.dispenseImgByIP("39.98.124.97", docker_image_name, Pmip);
                        } catch (Exception e) {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }
                        if (ans.contains("200")) {
                            if (ans.contains("-1")) {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }
                        } else {
                            task.setSTATUS(5);
                            taskUtils.setTask_status(5);
                            task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                            flag = true;
                            taskService.updateById(task);
                            break;
                        }
                        for(int i = 1; i <= vmNum;i++) {
                            String vmi_name = vm_name+"_"+cnt;
                            cnt++;
                            System.out.println("节点"+node.getNodeName()+"创建第"+i+"个虚拟机");
                            //创建虚拟机
                            try {
                                ans = processUtils.createVM(vm_image_name,vmi_name,memory,cpu_num,OStype,nettype,Pmip);
                                if (ans.contains("200")) {
                                    if (ans.contains("重复")) {
                                        task.setSTATUS(5);
                                        taskUtils.setTask_status(5);
                                        task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                        flag = true;
                                        taskService.updateById(task);
                                        break;
                                    }else {
                                        QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
                                        qw1.eq("name", vmi_name);
                                        VMInfo2 vm = vmService.getOne(qw1);
                                        if (vm.getIp() == null) {
                                            boolean ff = false;
                                            for (int j = 0; j < 8; ++j) {
                                                if (vmService.getOne(qw1).getIp() == null || vmService.getOne(qw1).getIp().isEmpty()) {
                                                    Thread.sleep(6000);
                                                    libvirtService.getallVMip(Pmip);
                                                } else {
                                                    ff = true;

                                                    if(ips.equals("")){
                                                        ips = vmService.getOne(qw1).getIp();
                                                    }else
                                                    {
                                                        System.out.println("节点"+node.getNodeName()+"第"+i+"个虚拟机获得IP:"+vmService.getOne(qw1).getIp());
                                                        ips +=","+vmService.getOne(qw1).getIp();
                                                    }

                                                    break;
                                                }
                                            }
                                            if(!ff) {
                                                task.setSTATUS(5);
                                                taskUtils.setTask_status(5);
                                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                                flag = true;
                                                taskService.updateById(task);
                                                break;
                                            }
                                        }else {
                                            System.out.println("第"+i+"个虚拟机获得IP:"+vmService.getOne(qw1).getIp());
                                            if(ips.equals("")){
                                                ips = vmService.getOne(qw1).getIp();
                                            }else
                                            {
                                                ips +=","+vmService.getOne(qw1).getIp();
                                            }
                                        }
                                    }
                                } else {
                                    task.setSTATUS(5);
                                    taskUtils.setTask_status(5);
                                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                    flag = true;
                                    taskService.updateById(task);
                                    break;
                                }
                            } catch (Exception e) {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }


                            System.out.println("节点"+node.getNodeName()+"第"+i+"个虚拟机执行命令创建文件夹");
                            //执行命令创建文件夹
                            QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
                            qw1.eq("name", vmi_name);
                            VMInfo2 vm = vmService.getOne(qw1);
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);

                            // 构建请求体
                            Map<String, Object> requestBody = new HashMap<>();
                            requestBody.put("host", vm.getIp());
                            requestBody.put("username", vm.getUsername());
                            requestBody.put("password", vm.getPasswd());
                            List<String> commands = Arrays.asList(
                                    mkcommand
                            );
                            requestBody.put("commands", commands);
                            System.out.println(commands);
                            // 发起请求
                            ResponseEntity<String> response = new RestTemplate().exchange(
                                    url,
                                    HttpMethod.POST,
                                    new HttpEntity<>(requestBody, headers),
                                    String.class
                            );
                            if (!response.toString().contains("\"exitStatus\":0")) {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }
                            //上传文件到虚拟机

                            System.out.println("节点"+node.getNodeName()+"第"+i+"个虚拟机上传文件到虚拟机");
                            try {
                                ans = processUtils.uploadDockerToVM(docker_image_name, vmi_name, Pmip, "39.98.124.97");
                            } catch (Exception e) {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }
                            System.out.println(ans);
                            if (ans.contains("200")) {
                                if (ans.contains("-1")) {
                                    task.setSTATUS(5);
                                    taskUtils.setTask_status(5);
                                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                    flag = true;
                                    taskService.updateById(task);
                                    break;
                                }
                            } else {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }

                            System.out.println("节点"+node.getNodeName()+"第"+i+"个虚拟机导入镜像");
                            //导入镜像
                            try {
                                ans = processUtils.importImage(docker_image_name, vmi_name, Pmip);
                            } catch (Exception e) {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }
                            System.out.println(ans);
                            if (ans.contains("200")) {
                                if (ans.contains("-1")) {
                                    task.setSTATUS(5);
                                    flag = true;
                                    taskUtils.setTask_status(5);
                                    task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                    taskService.updateById(task);
                                    break;
                                }
                            } else {
                                task.setSTATUS(5);
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                flag = true;
                                taskService.updateById(task);
                                break;
                            }

                            System.out.println("节点"+node.getNodeName()+"第"+i+"个虚拟机执行命令运行镜像");
                            //执行命令运行镜像
                            HttpHeaders headers1 = new HttpHeaders();
                            headers1.setContentType(MediaType.APPLICATION_JSON);

                            // 构建请求体
                            Map<String, Object> requestBody1 = new HashMap<>();
                            requestBody1.put("host", vm.getIp());
                            requestBody1.put("username", vm.getUsername());
                            requestBody1.put("password", vm.getPasswd());
                            List<String> commands1 = Arrays.asList(
                                    com
                            );
                            requestBody1.put("commands", commands1);
                            System.out.println(commands);
                            // 发起请求
                            ResponseEntity<String> response1 = new RestTemplate().exchange(
                                    url,
                                    HttpMethod.POST,
                                    new HttpEntity<>(requestBody1, headers1),
                                    String.class
                            );
                            if (!response1.toString().contains("\"exitStatus\":0")) {
                                task.setSTATUS(5);
                                flag = true;
                                taskUtils.setTask_status(5);
                                task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                                taskService.updateById(task);
                                break;
                            }
                        }
                    }
                    if(flag == false){
                        task.setSTATUS(4);
                        taskUtils.setVm_ip(ips);
                        taskUtils.setTask_status(4);
                        task.setTASK_ATTRIBUTES_VALUES(mapper.writeValueAsString(taskUtils));
                        taskService.updateById(task);
                    }
                }
            }
        }
        System.out.println("任务结束");
    }



    /**
     * 一步执行任务
     *
     * @param taskName 任务名
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ApiOperation(value = "执行任务", notes = "根据任务名查找数据库按照任务顺序执行子任务")
    @ResponseBody
    public CommonResp startTask(@RequestParam String taskName) throws Exception {
        ProcessUtils processUtils = new ProcessUtils();
        QueryWrapper<ConstructionInfo> qw = new QueryWrapper<>();
        qw.eq("TaskName", taskName);
        qw.orderByAsc("OperationOrder");
        List<ConstructionInfo> tasks = constructionService.list(qw);
        for (ConstructionInfo task :
                tasks) {
            System.out.println("=================");
            System.out.println("步骤" + task.getOperationOrder());
            if (task.getTaskType().contains("CreateVM")) {
                String ans = processUtils.createVM(task.getImgName(), task.getVmName(), task.getMemory(), task.getCPUNum(), task.getOSType(), task.getNetType(), task.getServerIp());
                System.out.println(ans);
                if (ans.contains("200")) {
                    if (ans.contains("重复")) {
                        return new CommonResp(false,"","创建失败，虚拟机名重复");
                    } else {
                        task.setOperationStatus(1);
                        constructionService.updateById(task);
                    }
                } else {
                    return new CommonResp(false,"","创建失败");
                }
            } else if (task.getTaskType().contains("Dispense")) {
                String ans = processUtils.dispenseImgByIP("39.98.124.97", task.getFileName(), task.getServerIp());
                System.out.println(ans);
                if (ans.contains("200")) {
                    if (ans.contains("-1")) {
                        return new CommonResp(false,"","文件下发失败");
                    } else {
                        task.setOperationStatus(1);
                        constructionService.updateById(task);
                    }
                } else {
                    return new CommonResp(false,"","文件下发失败");
                }
            } else if (task.getTaskType().contains("Upload")) {
                QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
                qw1.eq("name", task.getVmName());
                VMInfo2 vm = vmService.getOne(qw1);
                if (vm.getIp() == null) {
                    boolean flag = false;
                    for (int i = 0; i < 8; ++i) {
                        if (vmService.getOne(qw1).getIp() == null || vmService.getOne(qw1).getIp().isEmpty()) {
                            Thread.sleep(6000);
                            libvirtService.getallVMip(task.getServerIp());
                        } else {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false)
                        return new CommonResp(false,"","虚拟机IP不存在，上传文件失败");
                }
                String ans = processUtils.uploadDockerToVM(task.getFileName(), task.getVmName(), task.getServerIp(), "39.98.124.97");
                System.out.println(ans);
                if (ans.contains("200")) {
                    if (ans.contains("-1")) {
                        return new CommonResp(false,"","上传文件失败");
                    } else {
                        task.setOperationStatus(1);
                        constructionService.updateById(task);
                    }
                } else {
                    return new CommonResp(false,"","上传文件失败");
                }
            } else if (task.getTaskType().contains("Import")) {
                String ans = processUtils.importImage(task.getFileName(), task.getVmName(), task.getServerIp());
                System.out.println(ans);
                if (ans.contains("200")) {
                    if (ans.contains("-1")) {
                        return new CommonResp(false,"","导入镜像失败");
                    } else {
                        task.setOperationStatus(1);
                        constructionService.updateById(task);
                    }
                } else {
                    return new CommonResp(false,"","导入镜像失败");
                }
            } else if (task.getTaskType().contains("ExecuteCommand")) {
                String url = "http://" + task.getServerIp() + sufixUrl;
                String command = task.getCmd();
                QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
                qw1.eq("name", task.getVmName());
                VMInfo2 vm = vmService.getOne(qw1);
                if (vm == null || vm.getIp() == null)
                    return  new CommonResp(false,"","虚拟机IP不存在");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // 构建请求体
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("host", vm.getIp());
                requestBody.put("username", vm.getUsername());
                requestBody.put("password", vm.getPasswd());
                List<String> commands = Arrays.asList(
                        command
                );
                requestBody.put("commands", commands);
                System.out.println(commands);
                // 发起请求
                ResponseEntity<String> response = new RestTemplate().exchange(
                        url,
                        HttpMethod.POST,
                        new HttpEntity<>(requestBody, headers),
                        String.class
                );
                if (!response.toString().contains("\"exitStatus\":0")) {
                    return  new CommonResp(false,"","执行命令失败");
                }else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                }
            } else {
                return new CommonResp(false,"","任务类型不存在");
            }
        }
        return new CommonResp(true,"","任务执行成功");
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ApiOperation(value = "执行子任务", notes = "执行任务中的一个子任务")
    @ResponseBody
    public CommonResp processTask(@RequestParam String taskName, @RequestParam String taskType, @RequestParam Integer order) throws Exception {
        ProcessUtils processUtils = new ProcessUtils();
        QueryWrapper<ConstructionInfo> qw = new QueryWrapper<>();
        qw.eq("TaskName", taskName);
        qw.eq("TaskType", taskType);
        qw.eq("OperationOrder", order);
        ConstructionInfo task = constructionService.getOne(qw);
        if (taskType.contains("CreateVM")) {
            String ans = processUtils.createVM(task.getImgName(), task.getVmName(), task.getMemory(), task.getCPUNum(), task.getOSType(), task.getNetType(), task.getServerIp());
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("重复")) {
                    return new CommonResp(false,"","创建失败，虚拟机名重复");
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return  new CommonResp(true,"","虚拟机创建成功");
                }
            } else {
                return new CommonResp(false,"","创建失败");
            }
        } else if (taskType.contains("Dispense")) {
            String ans = processUtils.dispenseImgByIP("39.98.124.97", task.getFileName(), task.getServerIp());
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("-1")) {
                    return new CommonResp(false,"","文件下发失败");
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return new CommonResp(true,"","文件下发成功");
                }
            } else {
                return new CommonResp(false,"","文件下发失败");
            }
        } else if (taskType.contains("Upload")) {
            QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
            qw1.eq("name", task.getVmName());
            VMInfo2 vm = vmService.getOne(qw1);
            if (vm.getIp() == null)
                return new CommonResp(false,"","虚拟机IP不存在，上传文件失败");
            String ans = processUtils.uploadDockerToVM(task.getFileName(), task.getVmName(), task.getServerIp(), "39.98.124.97");
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("-1")) {
                    return new CommonResp(false,"","文件上传失败");
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return new CommonResp(true,"","文件上传成功");
                }
            } else {
                return new CommonResp(false,"","文件上传失败");
            }
        } else if (taskType.contains("Import")) {
            String ans = processUtils.importImage(task.getFileName(), task.getVmName(), task.getServerIp());
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("-1")) {
                    return new CommonResp(false,"","导入镜像失败");
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return new CommonResp(true,"","导入镜像成功");
                }
            } else {
                return new CommonResp(false,"","导入镜像失败");
            }
        } else if (taskType.contains("ExecuteCommand")) {

            String url = "http://" + task.getServerIp() + sufixUrl;
            String command = task.getCmd();
            QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
            qw1.eq("name", task.getVmName());
            VMInfo2 vm = vmService.getOne(qw1);
            if (vm == null || vm.getIp() == null)
                return new CommonResp(false,"","虚拟机IP不存在，执行命令失败");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("host", vm.getIp());
            requestBody.put("username", vm.getUsername());
            requestBody.put("password", vm.getPasswd());
            List<String> commands = Arrays.asList(
                    command
            );
            requestBody.put("commands", commands);
            System.out.println(commands);
            // 发起请求
            ResponseEntity<String> response = new RestTemplate().exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, headers),
                    String.class
            );
            if (!response.toString().contains("\"exitStatus\":0")) {
                return new CommonResp(false,"","执行命令失败");
            }else {
                task.setOperationStatus(1);
                constructionService.updateById(task);
            }
        } else {
            return  new CommonResp(false,"","任务类型不存在");
        }
        return new CommonResp(true,"","执行任务成功");
    }

}
