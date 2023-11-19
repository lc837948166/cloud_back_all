package com.xw.cloud.controller;

import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.bean.*;
import com.xw.cloud.service.LibvirtService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@Controller
public class LibvirtController {

    @Resource(name = "libvirtService")
    private LibvirtService libvirtService;

    @RequestMapping(value = {"/index"})
    public String index(Model model) {
        Host hostInfo = libvirtService.getHostInfo();
        System.out.println(hostInfo);
        model.addAttribute("hostinfo", hostInfo);
        LibvirtConnect connectInformation = libvirtService.getLibvirtConnectInformation();
        model.addAttribute("connectInformation", connectInformation);
        return "index";
    }

    @RequestMapping("/main")
    public String main(Model model) {
        List<Virtual> virtualList = libvirtService.getVirtualList();
        model.addAttribute("virtualList", virtualList);
        String netState = libvirtService.getNetState();
        model.addAttribute("netState", netState);
        return "main";

    }

    // 新增，获取虚拟机列表
    @ResponseBody
    @GetMapping("/getVMList")
    public List<Virtual> getVMList() {
        return libvirtService.getVirtualList();
    }

    @RequestMapping("/openOrCloseNetWork")
    public String openOrCloseNetWork(@RequestParam("netState") String netState) {
        if ("on".equals(netState)) libvirtService.closeNetWork();
        if ("off".equals(netState)) libvirtService.openNetWork();
        return "redirect:main";
    }

    @SneakyThrows
    @RequestMapping("/initiate/{name}")
    @ResponseBody
    public CommentResp initiateVirtual(@PathVariable("name") String name) {
        libvirtService.initiateDomainByName(name);
        return new CommentResp(true, null,"启动成功");
    }

    @SneakyThrows
    @RequestMapping("/suspended/{name}")
    @ResponseBody
    public CommentResp suspendedVirtual(@PathVariable("name") String name) {
        libvirtService.suspendedDomainName(name);
        return new CommentResp(true, null,"挂起成功");
    }

    @SneakyThrows
    @RequestMapping("/resume/{name}")
    @ResponseBody
    public CommentResp resumeVirtual(@PathVariable("name") String name) {
        libvirtService.resumeDomainByName(name);
        return new CommentResp(true, null,"还原成功");
    }

    @SneakyThrows
    @RequestMapping("/save")
    public String saveVirtual(@RequestParam("name") String name) {
        libvirtService.saveDomainByName(name);
        return "redirect:main";
    }

    @SneakyThrows
    @RequestMapping("/restore")
    public String restoreVirtual(@RequestParam("name") String name) {
        libvirtService.restoreDomainByName(name);
        return "redirect:main";
    }

    @SneakyThrows
    @RequestMapping("/shutdown/{name}")
    @ResponseBody
    public CommentResp shutdownVirtual(@PathVariable("name") String name) {
        libvirtService.shutdownDomainByName(name);
        return new CommentResp(true, null,"关机成功");
    }

    @SneakyThrows
    @RequestMapping("/shutdownMust/{name}")
    @ResponseBody
    public CommentResp shutdownMustVirtual(@PathVariable("name") String name) {
        libvirtService.shutdownMustDomainByName(name);
        return new CommentResp(true, null,"强行关机成功");
    }

    @SneakyThrows
    @RequestMapping("/reboot/{name}")
    @ResponseBody
    public CommentResp rebootVirtual(@PathVariable("name") String name) {
        libvirtService.rebootDomainByName(name);
        return new CommentResp(true, null,"正在重启");
    }

    @RequestMapping(value = "/delete/{name}",method = RequestMethod.DELETE)
    @ResponseBody
    public CommentResp deleteVirtual(@PathVariable("name") String name) {
        libvirtService.deleteDomainByName(name);
        libvirtService.deleteImgFile(name + ".qcow2");
        return new CommentResp(true, null,"删除成功");
    }

    @RequestMapping("/toAddVirtual")
    public String toAddVirtual(Model model) {
        String netState = libvirtService.getNetState();
        model.addAttribute("netState", netState);
        return "addVirtual";
    }

//    @ResponseBody
//    @PostMapping(value = "/addVirtual")
//    public String addVirtual(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
//                             @RequestParam("memory") String memory, @RequestParam("cpuNum") String cpuNum,
//                             @RequestParam("OStype") String OStype) {
//        VM_create vmc = new VM_create();
//        vmc.setName(name);
//        vmc.setMemory(Long.parseLong(memory));
//        vmc.setCpuNum(Integer.parseInt(cpuNum));
//        vmc.setOStype(OStype);
//        libvirtService.addDomainByName(vmc);
//        libvirtService.addImgFile(vmc.getName(), file);
//        return "main";
//    }
    @ResponseBody
    @RequestMapping("/addVirtual")
    public String addVirtual(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                             @RequestParam("memory") int memory, @RequestParam("cpuNum") String cpuNum,
                             @RequestParam("OStype") String OStype) {
        VM_create vmc = new VM_create();
        vmc.setName(name);
        vmc.setMemory(memory);
        vmc.setCpuNum(Integer.parseInt(cpuNum));
        vmc.setOStype(OStype);
        libvirtService.addDomainByName(vmc);
        libvirtService.addImgFile(vmc.getName(), file);
        return "ok";
    }




    @RequestMapping("/getSnapshotList")
    public String getSnapshotList(@RequestParam("name") String name,
                                  Model model) {
        List<Snapshot> snapshotList = libvirtService.getSnapshotListByName(name);
        model.addAttribute("snapshotList", snapshotList);
        model.addAttribute("virtualName", name);
        return "snapshot";
    }

    @SneakyThrows
    @RequestMapping("/deleteSnapshot")
    public String deleteSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        libvirtService.deleteSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @SneakyThrows
    @RequestMapping("/revertSnapshot")
    public String revertSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        libvirtService.revertSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @SneakyThrows
    @RequestMapping("/createSnapshot")
    public String createSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        libvirtService.createSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @RequestMapping("/storagePoolList")
    public String storagePoolList(Model model) {
        List<Storagepool> storagePoolList = libvirtService.getStoragePoolList();
        model.addAttribute("storagePoolList", storagePoolList);
        String netState = libvirtService.getNetState();
        model.addAttribute("netState", netState);
        return "storagepool";
    }

    @SneakyThrows
    @RequestMapping("/deleteStoragePool")
    public String deleteStoragePool(@RequestParam("name") String name) {
        libvirtService.deleteStoragePool(name);
        Thread.sleep(1000);
        return "redirect:/storagePoolList";
    }

    @RequestMapping("/toCreateStoragepool")
    public String toCreateStoragepool(Model model) {
        String netState = libvirtService.getNetState();
        model.addAttribute("netState", netState);
        return "addStoragepool";
    }

    @SneakyThrows
    @RequestMapping("/createStoragepool")
    public String createStoragepool(@RequestParam("storagepoolName") String name,
                                    @RequestParam("storagepoolPath") String url) {
        libvirtService.createStoragepool(name, url);
        Thread.sleep(1000);
        return "redirect:/storagePoolList";
    }

}
