package com.xw.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.Utils.RemoteVMUtils;
import com.xw.cloud.Utils.SftpUtils;
import com.xw.cloud.bean.*;
import com.xw.cloud.inter.OperationLogDesc;
import com.xw.cloud.mapper.IpaddrMapper;
import com.xw.cloud.mapper.VmMapper;
import com.xw.cloud.service.LibvirtService;
import com.xw.cloud.service.VmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "虚拟化资源管理", description = "管理虚拟机、快照和存储池等虚拟化资源")
@CrossOrigin
@Controller
public class LibvirtController {

    @Resource(name = "libvirtService")
    private LibvirtService libvirtService;

    @Autowired
    private VmService vmService;

    @Resource
    private IpaddrMapper ipaddrMapper;

    @Resource
    private VmMapper vmMapper;

    @ApiOperation(value = "虚拟化主页", notes = "返回虚拟化管理的主页")
    @RequestMapping(value = {"/index"})
    public String index(Model model) {
        Host hostInfo = libvirtService.getHostInfo();
        System.out.println(hostInfo);
        model.addAttribute("hostinfo", hostInfo);
        LibvirtConnect connectInformation = libvirtService.getLibvirtConnectInformation();
        model.addAttribute("connectInformation", connectInformation);
        return "index";
    }

    @ApiOperation(value = "主界面", notes = "返回虚拟机和网络状态的主界面")
    @RequestMapping("/main")
    public String main(Model model) {
        List<Virtual> virtualList = libvirtService.getVirtualList();
        model.addAttribute("virtualList", virtualList);
        String netState = libvirtService.getNetState();
        model.addAttribute("netState", netState);
        return "main";

    }

    @ApiOperation(value = "获取虚拟机列表", notes = "列出所有的虚拟机")
    @ResponseBody
    @SneakyThrows
    @GetMapping("/getVMList/{ip:.*}")
    @OperationLogDesc(module = "虚拟机管理", events = "获取虚拟机列表")
    public List getVMList(@PathVariable("ip") String ip) {
        System.out.println(ip);
        StringBuilder response = RemoteVMUtils.httputil("http://" + ip + ":8080/getVMList");
            ObjectMapper mapper1 = new ObjectMapper();
            mapper1.readValue(response.toString(), List.class);
            // 处理响应
            return mapper1.readValue(response.toString(), List.class);
        }

    @ResponseBody
    @SneakyThrows
    @GetMapping("/getVMList2/{ip:.*}")
    public List<VMInfo2> getVMList2(@PathVariable("ip") String ip) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("SERVERIP",ip);
        List<VMInfo2> list = vmMapper.selectList(qw);
        // 处理响应
        return list;
    }
    @ApiOperation(value = "获取虚拟机指标列表", notes = "列出所有的虚拟机指标")
    @ResponseBody
    @SneakyThrows
    @GetMapping("/getVMIndexList/{ip:.*}")
    @OperationLogDesc(module = "虚拟机管理", events = "获取虚拟机指标列表")
    public List<Virtual> getVMIndexList(@PathVariable("ip") String ip) {
        StringBuilder response = RemoteVMUtils.httputil("http://" + ip + ":8080/getVMIndexList");
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), List.class);
//        return libvirtService.getIndexList();
    }

    @ApiOperation(value = "获取虚拟机指标列表", notes = "列出虚拟机指标")
    @SneakyThrows
    @ResponseBody
    @GetMapping("/getVMIndex/{ip:.*}")
    public CommentResp getVMIndex(@PathVariable("ip") String ip) {
        QueryWrapper<VMInfo2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", ip);
        VMInfo2 vminfo = vmMapper.selectOne(queryWrapper);
        if(vminfo.getServerip()!=null) {
                StringBuilder response = RemoteVMUtils.httputil("http://" + vminfo.getServerip() + ":8080/getVMIndex/" + ip);
                ObjectMapper mapper1 = new ObjectMapper();
                mapper1.readValue(response.toString(), CommentResp.class);
                // 处理响应
                return mapper1.readValue(response.toString(), CommentResp.class);
            }
        if(vminfo==null)return new CommentResp(false, 201, "当前ip不存在");

        Virtual virtual = libvirtService.getIndex(vminfo.getName(),vminfo.getUpBandWidth(),vminfo.getDownBandWidth());

        Map<String, Object> node = vmService.queryLatAndLon(ip);
        Map<String, Object> data = new HashMap<>();
        data.put("coordinate", node);
        data.put("virtual", virtual);

        return new CommentResp(true, data, "查询成功");
    }

//        return libvirtService.getIndex(ip);
//    }

    @ApiOperation(value = "开启/关闭网络", notes = "根据提供的网络状态开启或关闭网络")
    @RequestMapping("/openOrCloseNetWork")
    @OperationLogDesc(module = "虚拟机管理", events = "开启/关闭网络")
    public String openOrCloseNetWork(@RequestParam("netState") String netState) {
        if ("on".equals(netState)) libvirtService.closeNetWork();
        if ("off".equals(netState)) libvirtService.openNetWork();
        return "redirect:main";
    }

    @ApiOperation(value = "启动虚拟机", notes = "根据虚拟机名称启动虚拟机")
    @SneakyThrows
    @RequestMapping("/initiate/{name}")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "启动虚拟机")
    public CommentResp initiateVirtual(@PathVariable("name") String name) {
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputil("http://" + vmInfo2.getServerip() + ":8080/initiate/"+name);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "更改虚拟机配置", notes = "根据虚拟机名称更改虚拟机配置")
    @SneakyThrows
    @RequestMapping("/changeVM/{name}")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "更改虚拟机配置")
    public CommentResp changeVM(@PathVariable("name") String name,@RequestParam("cpuNum") int cpu,@RequestParam("memory") int mem) {
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputil("http://" + vmInfo2.getServerip() + ":8080/changeVM/"+name+"?memory="+mem+"&cpuNum="+cpu);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "挂起虚拟机", notes = "根据虚拟机名称挂起虚拟机")
    @SneakyThrows
    @RequestMapping("/suspended/{name}")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "挂起虚拟机")
    public CommentResp suspendedVirtual(@PathVariable("name") String name) {
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputil("http://" + vmInfo2.getServerip() + ":8080/suspended/"+name);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "恢复虚拟机", notes = "根据虚拟机名称恢复虚拟机")
    @SneakyThrows
    @RequestMapping("/resume/{name}")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "恢复虚拟机")
    public CommentResp resumeVirtual(@PathVariable("name") String name) {
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputil("http://" + vmInfo2.getServerip() + ":8080/resume/"+name);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "保存虚拟机", notes = "根据虚拟机名称保存虚拟机")
    @SneakyThrows
    @RequestMapping("/save")
    @OperationLogDesc(module = "虚拟机管理", events = "保存虚拟机")
    public String saveVirtual(@RequestParam("name") String name) {
        libvirtService.saveDomainByName(name);
        return "redirect:main";
    }

    @ApiOperation(value = "还原虚拟机", notes = "根据虚拟机名称还原虚拟机")
    @SneakyThrows
    @RequestMapping("/restore")
    @OperationLogDesc(module = "虚拟机管理", events = "还原虚拟机")
    public String restoreVirtual(@RequestParam("name") String name) {
        libvirtService.restoreDomainByName(name);
        return "redirect:main";
    }

    @ApiOperation(value = "关闭虚拟机", notes = "正常关闭指定的虚拟机")
    @SneakyThrows
    @RequestMapping("/shutdown/{name}")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "关闭虚拟机")
    public CommentResp shutdownVirtual(@PathVariable("name") String name) {
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputil("http://" + vmInfo2.getServerip() + ":8080/shutdown/"+name);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "强制关闭虚拟机", notes = "强制关闭指定的虚拟机")
    @SneakyThrows
    @RequestMapping("/shutdownMust/{name}")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "强制关闭虚拟机")
    public CommentResp shutdownMustVirtual(@PathVariable("name") String name) {
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputil("http://" + vmInfo2.getServerip() + ":8080/shutdownMust/"+name);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "重启虚拟机", notes = "重启指定的虚拟机")
    @SneakyThrows
    @RequestMapping("/reboot/{name}")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "重启虚拟机")
    public CommentResp rebootVirtual(@PathVariable("name") String name) {
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputil("http://" + vmInfo2.getServerip() + ":8080/reboot/"+name);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "删除虚拟机", notes = "删除指定的虚拟机和其关联的镜像文件")
    @SneakyThrows
    @RequestMapping(value = "/delete/{name}",method = RequestMethod.POST)
    @ResponseBody
    @OperationLogDesc(module = "虚拟机管理", events = "删除虚拟机")
    public CommentResp deleteVirtual(@PathVariable("name") String name) {
        String encodedChinese = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        VMInfo2 vmInfo2=vmMapper.selectById(name);
        StringBuilder response = RemoteVMUtils.httputilPost("http://" + vmInfo2.getServerip() + ":8080/delete/"+encodedChinese);
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "跳转至添加虚拟机页面", notes = "返回添加虚拟机的界面")
    @RequestMapping("/toAddVirtual")
    @ResponseBody
    public String toAddVirtual(@RequestParam("name") String name,@RequestParam("ip") String ip) {

            VMInfo2 vmInfo2 = new VMInfo2();
            vmInfo2.setName(name);
            String data= SftpUtils.getexecon1(ip,"cat /proc/net/dev | awk '{i++; if(i>2){print $1}}' | sed 's/^[\\t]*//g' | sed 's/[:]*$//g'");
            String[] lines = data.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            String str = lines[i];
            if (str.charAt(0) == 'e') {
                System.out.println("Index: " + i + ", String: " + str);
            }
        }

            System.out.println(lines[0]);
            return data;

    }

    @ApiOperation(value = "添加虚拟机", notes = "根据提供的信息添加新的虚拟机")
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/addVirtual")
    @OperationLogDesc(module = "虚拟机管理", events = "添加虚拟机")
    public CommentResp addVirtual(@RequestParam("ImgName") String ImgName, @RequestParam("name") String name,
                             @RequestParam("memory") int memory, @RequestParam("cpuNum") int cpuNum,
                             @RequestParam(value = "OStype", defaultValue = "X86") String OStype,
                                  @RequestParam("nettype") String NetType,
                                  @RequestParam("serverip") String serverip,
                                  @RequestParam(value = "usetype", required = false) String usetype,
                                  @RequestParam(value = "bandwidth", required = false) Integer bandwidth) throws InterruptedException {
        StringBuilder response;
        if(usetype==null&bandwidth==null){
            response = RemoteVMUtils.httputil(
                    "http://" + serverip + ":8080/addVirtual?ImgName="+ImgName
                            +"&name="+name+"&memory="+memory+"&cpuNum="+cpuNum+"&OStype="
                            +OStype+"&nettype="+NetType+"&serverip="+serverip);
        }
        else if(usetype==null){
            response = RemoteVMUtils.httputil(
                    "http://" + serverip + ":8080/addVirtual?ImgName="+ImgName
                            +"&name="+name+"&memory="+memory+"&cpuNum="+cpuNum+"&OStype="
                            +OStype+"&nettype="+NetType+"&serverip="+serverip+"&bandwidth="+bandwidth);
        }
        else if(bandwidth==null){
            response = RemoteVMUtils.httputil(
                    "http://" + serverip + ":8080/addVirtual?ImgName="+ImgName
                            +"&name="+name+"&memory="+memory+"&cpuNum="+cpuNum+"&OStype="
                            +OStype+"&nettype="+NetType+"&serverip="+serverip+"&usetype="+usetype);
        }
        else {
            response = RemoteVMUtils.httputil(
                    "http://" + serverip + ":8080/addVirtual?ImgName="+ImgName
                            +"&name="+name+"&memory="+memory+"&cpuNum="+cpuNum+"&OStype="
                            +OStype+"&nettype="+NetType+"&serverip="+serverip+"&usetype="
                            +usetype+"&bandwidth="+bandwidth);
        }
        ObjectMapper mapper1 = new ObjectMapper();
        // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ApiOperation(value = "获取快照列表", notes = "根据虚拟机名称获取其快照列表")
    @RequestMapping("/getSnapshotList")
    @OperationLogDesc(module = "虚拟机管理", events = "获取快照列表")
    public String getSnapshotList(@RequestParam("name") String name,
                                  Model model) {
        List<Snapshot> snapshotList = libvirtService.getSnapshotListByName(name);
        model.addAttribute("snapshotList", snapshotList);
        model.addAttribute("virtualName", name);
        return "snapshot";
    }

    @ApiOperation(value = "删除快照", notes = "删除指定虚拟机的特定快照")
    @SneakyThrows
    @RequestMapping("/deleteSnapshot")
    @OperationLogDesc(module = "虚拟机管理", events = "删除快照")
    public String deleteSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        libvirtService.deleteSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @ApiOperation(value = "还原快照", notes = "将指定虚拟机还原至特定快照的状态")
    @SneakyThrows
    @RequestMapping("/revertSnapshot")
    @OperationLogDesc(module = "虚拟机管理", events = "还原快照")
    public String revertSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        libvirtService.revertSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @ApiOperation(value = "创建快照", notes = "为指定虚拟机创建新的快照")
    @SneakyThrows
    @RequestMapping("/createSnapshot")
    @OperationLogDesc(module = "虚拟机管理", events = "创建快照")
    public String createSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        libvirtService.createSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @ApiOperation(value = "存储池列表", notes = "获取所有存储池的列表")
    @RequestMapping("/storagePoolList")
    @OperationLogDesc(module = "虚拟机管理", events = "存储池列表")
    public String storagePoolList(Model model) {
        List<Storagepool> storagePoolList = libvirtService.getStoragePoolList();
        model.addAttribute("storagePoolList", storagePoolList);
        String netState = libvirtService.getNetState();
        model.addAttribute("netState", netState);
        return "storagepool";
    }

    @ApiOperation(value = "存储池列表", notes = "获取所有存储池的列表")
    @SneakyThrows
    @RequestMapping("/deleteStoragePool")
    public String deleteStoragePool(@RequestParam("name") String name) {
        libvirtService.deleteStoragePool(name);
        Thread.sleep(1000);
        return "redirect:/storagePoolList";
    }

    @ApiOperation(value = "跳转至创建存储池页面", notes = "返回创建存储池的界面")
    @RequestMapping("/toCreateStoragepool")
    @OperationLogDesc(module = "虚拟机管理", events = "跳转至创建存储池页面")
    public String toCreateStoragepool(Model model) {
        String netState = libvirtService.getNetState();
        model.addAttribute("netState", netState);
        return "addStoragepool";
    }

    @ApiOperation(value = "跳转至创建存储池页面", notes = "返回创建存储池的界面")
    @SneakyThrows
    @RequestMapping("/createStoragepool")
    public String createStoragepool(@RequestParam("storagepoolName") String name,
                                    @RequestParam("storagepoolPath") String url) {
        libvirtService.createStoragepool(name, url);
        Thread.sleep(1000);
        return "redirect:/storagePoolList";
    }
}
