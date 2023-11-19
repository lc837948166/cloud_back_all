package com.xw.cloud.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcraft.jsch.*;
import com.xw.cloud.bean.OperationLog;
import com.xw.cloud.bean.PodLog;
import com.xw.cloud.bean.VMLog;
import com.xw.cloud.inter.OperationLogDesc;
import com.xw.cloud.resp.CasResp;
import com.xw.cloud.resp.CommentResp;
import com.xw.cloud.resp.SelectResp;
import com.xw.cloud.service.impl.OperationLogServiceImpl;
import com.xw.cloud.service.impl.PodLogServiceImpl;
import com.xw.cloud.service.impl.VMLogServiceImpl;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("log")
public class LogController {
    @Value("${k8s.config}")
    private String k8sConfig;

    private String virtualMachineIp = "192.168.243.143";

    private String username = "root";

    private String password = "111";

    @Autowired
    private OperationLogServiceImpl operationLogService;

    @Autowired
    private PodLogServiceImpl podLogService;

    @Autowired
    private VMLogServiceImpl vmLogService;

    @RequestMapping(value = "/getLogList", method = RequestMethod.GET)
    @ResponseBody
    @OperationLogDesc(module = "日志管理", events = "列表查询")
    public CommentResp getLogList(@RequestParam("operationModule")String operationModule,@RequestParam("operationStatus")String operationStatus,@RequestParam("starttime")String starttime,@RequestParam("endtime")String endtime) {
        QueryWrapper<OperationLog> qw = new QueryWrapper();
        if(operationModule!=null&& !operationModule.equals("")){
            qw.eq("OperationModule",operationModule);
        }
        if(operationStatus!=null && !operationStatus.equals("")){
            qw.eq("OperationStatus",operationStatus);
        }
        if(starttime!=null&&!starttime.equals("")&&!starttime.equals("Invalid date")){
            qw.ge("add_time",starttime);
        }
        if(endtime!=null&&!endtime.equals("")&&!endtime.equals("Invalid date")){
            qw.le("add_time",endtime);
        }
        System.out.println(starttime);
        List<OperationLog> list = operationLogService.list(qw);
        System.out.println(list);
        CommentResp com = new CommentResp(true,list,"");
        return com;
    }

    @DeleteMapping(value = "/deleteLog/{id}")
    @ResponseBody
    @OperationLogDesc(module = "日志管理", events = "日志删除")
    public CommentResp deleteLog(@PathVariable Long id) {
        boolean b = operationLogService.removeById(id);
        CommentResp com = new CommentResp(b,null,"");
        return com;
    }

    @DeleteMapping(value = "/deletePodLog/{id}")
    @ResponseBody
    @OperationLogDesc(module = "日志管理", events = "容器日志删除")
    public CommentResp deletePodLog(@PathVariable Long id) {
        boolean b = podLogService.removeById(id);
        CommentResp com = new CommentResp(b,null,"");
        return com;
    }

    @RequestMapping(value = "/getVMLog", method = RequestMethod.GET)
    @ResponseBody
    public CommentResp getVMLog(@RequestParam("VMName") String VMName,
                                @RequestParam("starttime") String starttime,
                                @RequestParam("endtime") String endtime) throws Exception {
        QueryWrapper<VMLog> qw = new QueryWrapper();
        if(VMName!=null&& !VMName.equals("")){
            qw.eq("VmName",VMName);
        }
        if(starttime!=null&&!starttime.equals("")&&!starttime.equals("Invalid date")){
            qw.ge("AddTime",starttime);
        }
        if(endtime!=null&&!endtime.equals("")&&!endtime.equals("Invalid date")){
            qw.le("AddTime",endtime);
        }
        List<VMLog> list = vmLogService.list(qw);
        CommentResp com = new CommentResp(true,list,"");
        return com;
    }

    @DeleteMapping(value = "/deleteVMLog/{id}")
    @ResponseBody
    @OperationLogDesc(module = "日志管理", events = "虚拟机日志删除")
    public CommentResp deleteVMLog(@PathVariable Long id) {
        boolean b = vmLogService.removeById(id);
        CommentResp com = new CommentResp(b,null,"");
        return com;
    }


    //前端调用方法  从数据库读取日志
    @RequestMapping(value = "/getPodLog", method = RequestMethod.GET)
    @ResponseBody
    public CommentResp getPodlog(@RequestParam("podNamespace") String podNamespace,
                                  @RequestParam("starttime") String starttime,
                                 @RequestParam("endtime") String endtime
    )
            throws IOException, InterruptedException {

        QueryWrapper<PodLog> qw = new QueryWrapper();
        if(podNamespace!=null&& !podNamespace.equals("")){
            String[] split = podNamespace.split("/");
            String namespace = split[0];
            String podname = split[1];
            System.out.println("namespace:"+namespace);
            System.out.println("podname:"+podname);
            qw.eq("PodName",podname);
            qw.eq("Spaces",namespace);
        }

        if(starttime!=null&&!starttime.equals("")&&!starttime.equals("Invalid date")){
            qw.ge("AddTime",starttime);
        }
        if(endtime!=null&&!endtime.equals("")&&!endtime.equals("Invalid date")){
            qw.le("AddTime",endtime);
        }
        List<PodLog> list = podLogService.list(qw);
        System.out.println(list);
        CommentResp com = new CommentResp(true,list,"");
        return com;
    }

    //测试方法 从虚拟机读取日志
    @RequestMapping(value = "/getPodLogs", method = RequestMethod.GET)
    @ResponseBody
    public CommentResp getPodlogs() throws IOException, ApiException {

        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api();
        List<PodLog> ans = new LinkedList<>();
        // 获取Pod的日志
        V1NamespaceList v1NamespaceList = api.listNamespace(null,null,null,null,null,null,null,null,null,null);
        List<V1Namespace> items = v1NamespaceList.getItems();
        for(V1Namespace v1: items){
            String namesapceName = v1.getMetadata().getName();

            V1PodList v1PodList = api.listNamespacedPod(namesapceName, null, null, null, null, null, null, null, null, null, null);
            for (V1Pod pod : v1PodList.getItems()) {
                String name = pod.getMetadata().getName();
                String podLogs = null;
                try {
                    podLogs = api.readNamespacedPodLog(name, namesapceName, null,null, null, null, null, null, null, null, true);
                }catch (ApiException ae){

                }
                if(podLogs!=null&&!podLogs.equals("")) {
                    String[] split = podLogs.split("\n");
                    for (String s : split) {
                        PodLog l = new PodLog();
                        l.setPodNames(name);
                        l.setSpaces(namesapceName);
                        l.setPodContent(s);
                        ans.add(l);
                    }
                }
            }
        }
        return new CommentResp(true,ans,"");
    }

    @RequestMapping(value = "/getCas", method = RequestMethod.GET)
    @OperationLogDesc(module = "日志管理", events = "查询命名空间和容器名")
    @ResponseBody
    public CommentResp getCas() throws IOException, ApiException {

        // 通过流读取，方式1
        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
        // 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));
        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api();
        V1NamespaceList v1NamespaceList = api.listNamespace(null,null,null,null,null,null,null,null,null,null);
        List<V1Namespace> items = v1NamespaceList.getItems();
       /* HashMap<String, List<String>> cas = new HashMap<>();
        for(V1Namespace v1: items){
            String namesapceName = v1.getMetadata().getName();
            List<String> pods = new LinkedList<>();
            V1PodList v1PodList = api.listNamespacedPod(namesapceName, null, null, null, null, null, null, null, null, null, null);
            for (V1Pod pod : v1PodList.getItems()) {
                pods.add(pod.getMetadata().getName());
            }
            if(pods.size()>0)
                cas.put(namesapceName,pods);
        }*/
        List<CasResp> cas = new LinkedList<>();
        for(V1Namespace v1: items){
            String namesapceName = v1.getMetadata().getName();
            CasResp cass = new CasResp();
            cass.setLabel(namesapceName);
            cass.setValue(namesapceName);
            List<CasResp> pods = new LinkedList<>();
            V1PodList v1PodList = api.listNamespacedPod(namesapceName, null, null, null, null, null, null, null, null, null, null);
            for (V1Pod pod : v1PodList.getItems()) {
                CasResp po = new CasResp();
                po.setLabel(pod.getMetadata().getName());
                po.setValue(pod.getMetadata().getName());
                pods.add(po);
            }
            if(pods.size()>0){
                cass.setChildren(pods);
                cas.add(cass);
            }
        }
        return new CommentResp(true,cas,"");
    }




    @RequestMapping(value = "/getVMName", method = RequestMethod.GET)
    @OperationLogDesc(module = "虚拟机日志管理", events = "查询虚拟机名")
    @ResponseBody
    public CommentResp getVMName() throws IOException, ApiException, JSchException {

        Session session = null;
        StringBuilder result = new StringBuilder();
        JSch jsch = new JSch();
        session = jsch.getSession(username, virtualMachineIp, 22);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();
        // 执行命令
        Channel execChannel = session.openChannel("exec");
        ((ChannelExec) execChannel).setCommand("ls /var/log/libvirt/qemu"); // 设置执行的命令
        InputStream in = null;
        in = execChannel.getInputStream();  // 获取命令执行结果的输入流
        execChannel.connect();  // 连接远程执行命令
        byte[] tmp = new byte[1024];
        StringBuilder commandOutput = new StringBuilder();
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                commandOutput.append(new String(tmp, 0, i));
            }
            if (execChannel.isClosed()) {
                if (in.available() > 0) continue;
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
                // 处理异常
            }
        }

        List<SelectResp> selectResps = new LinkedList<>();
        for(String vmName: commandOutput.toString().split("\n")){
            commandOutput.setLength(0);
            if(!vmName.contains(".log"))
                continue;
            String vname = vmName.substring(0,vmName.indexOf("log")-1);
            SelectResp selectResp = new SelectResp();
            selectResp.setLabel(vname);
            selectResp.setValue(vname);
            selectResps.add(selectResp);
        }
        return new CommentResp(true,selectResps,"");
    }
}
