package com.xw.cloud.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xw.cloud.Utils.ProcessUtils;

import com.xw.cloud.bean.ConstructionInfo;
import com.xw.cloud.bean.VMInfo2;
import com.xw.cloud.service.ConstructionService;
import com.xw.cloud.service.VmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;


@CrossOrigin
@Controller
public class ProcessController {

    @Autowired
    private  ConstructionService constructionService;
    @Autowired
    private VmService vmService;

    private final String sufixUrl = ":8081/api/ssh/execute2";

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public String processTask(@RequestParam String taskName) throws Exception {
        ProcessUtils processUtils = new ProcessUtils();
        QueryWrapper<ConstructionInfo> qw = new QueryWrapper<>();
        qw.eq("TaskName", taskName);
        ConstructionInfo task = constructionService.getOne(qw);
        if (taskName.contains("CreateVM")) {
            String ans = processUtils.createVM(task.getImgName(), task.getVmName(), task.getMemory(), task.getCPUNum(), task.getOSType(), task.getNetType(), task.getServerIp());
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("重复")) {
                    return "创建失败，虚拟机名重复";
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return "虚拟机创建成功";
                }
            } else {
                return "连接失败";
            }
        } else if (taskName.contains("Dispense")) {
            String ans = processUtils.dispenseImgByIP("39.98.124.97", task.getFileName(), task.getServerIp());
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("-1")) {
                    return "文件下发失败";
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return "文件下发成功";
                }
            } else {
                return "连接失败";
            }
        } else if (taskName.contains("Upload")) {
            QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
            qw1.eq("name",task.getVmName());
            VMInfo2 vm = vmService.getOne(qw1);
            if(vm.getIp() == null)
                return "虚拟机IP不存在，上传文件失败";
            String ans = processUtils.uploadDockerToVM(task.getFileName(),task.getVmName(),task.getServerIp(),"39.98.124.97");
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("-1")) {
                    return "上传失败";
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return "上传成功";
                }
            } else {
                return "连接失败";
            }
        }else if(taskName.contains("Import")){
            String ans = processUtils.importImage(task.getFileName(),task.getVmName(),task.getServerIp());
            System.out.println(ans);
            if (ans.contains("200")) {
                if (ans.contains("-1")) {
                    return "导入镜像失败";
                } else {
                    task.setOperationStatus(1);
                    constructionService.updateById(task);
                    return "导入镜像成功";
                }
            } else {
                return "连接失败";
            }
        }else if(taskName.contains("ExecuteCommand")){

            String url = "http://" + task.getServerIp() + sufixUrl;
            String command = task.getCmd();
            QueryWrapper<VMInfo2> qw1 = new QueryWrapper();
            qw1.eq("name",task.getVmName());
            VMInfo2 vm = vmService.getOne(qw1);
            if(vm==null|| vm.getIp() == null)
                return "虚拟机不存在或IP不存在";
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

            System.out.println(response);
            return response.toString();
        }else {
            return "error";
        }
    }
}
