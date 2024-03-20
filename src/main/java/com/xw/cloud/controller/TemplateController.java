package com.xw.cloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xw.cloud.Utils.RemoteVMUtils;
import com.xw.cloud.inter.OperationLogDesc;
import com.xw.cloud.mapper.PodTemplateMapper;
import com.xw.cloud.mapper.TemplateMapper;
import com.xw.cloud.bean.*;
import com.xw.cloud.service.LibvirtService;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.xw.cloud.Utils.CommentResp;

import java.io.InputStream;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.annotation.Resource;
@Api(tags = "模版管理", description = "提供模版管理")
@CrossOrigin
@Controller
@RequestMapping("/Template")
public class TemplateController {

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private PodTemplateMapper podTemplateMapper;

    @Resource(name = "libvirtService")
    private LibvirtService libvirtService;
    //通过id得到用户信息
//    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
//    public String getUser(@PathVariable int id){
//        return templateService.gettemp(id).toString();
//    }
    //通过id删除用户信息
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public CommentResp delete(@PathVariable("id") int id){
        System.out.println(id);
        int result = templateMapper.deleteById(id);
        if(result >= 1){
            return new CommentResp(true, null,"删除成功");
        }else{
            return new CommentResp(false, null,"删除失败");
        }
    }

    @RequestMapping(value = "/deletePodTemplate/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public CommentResp deletePodTemplate(@PathVariable("id") int id){
        System.out.println(id);
        int result = podTemplateMapper.deleteById(id);
        if(result >= 1){
            return new CommentResp(true, null,"删除成功");
        }else{
            return new CommentResp(false, null,"删除失败");
        }
    }

    //更改用户信息
    @RequestMapping(value = "/update")
    @ResponseBody
    @OperationLogDesc(module = "模板管理", events = "模板更新")
    public CommentResp update(@RequestBody Template temp){
        System.out.println(temp);
        int result = templateMapper.updateById(temp);
        if (result >= 1) {
            return new CommentResp(true, null,"更新成功");
        } else {
            return new CommentResp(false, null,"更新失败");
        }
    }

    @PostMapping(value = "/updatePodtemp")
    @ResponseBody
    @OperationLogDesc(module = "模板管理", events = "模板更新")
    public CommentResp updatePodtemp(@RequestBody PodTemplate podTemplate){
        int result = podTemplateMapper.updateById(podTemplate);
        if (result >= 1) {
            return new CommentResp(true, null,"更新成功");
        } else {
            return new CommentResp(false, null,"更新失败");
        }
    }

    //插入用户信息
    @RequestMapping(value = "/insert")
    @ResponseBody
    @OperationLogDesc(module = "模板管理", events = "添加模板")
    public CommentResp insert(@RequestBody Template temp) {
        int result = templateMapper.insert(temp);
        if (result >= 1) {
            return new CommentResp(true, null,"插入成功");
        } else {
            return new CommentResp(false, null,"插入失败");
        }
    }



    //查询所有用户的信息
    @RequestMapping(value = "/selectAll")
    @ResponseBody   //理解为：单独作为响应体，这里不调用实体类的toString方法
    public CommentResp  listUser(){
        List<Template> tempList = templateMapper.selectList(null);
        return new CommentResp(true, tempList,"");
    }

    // 获取全部容器模板
    @GetMapping("/getAllPodsTemplates")
    @ResponseBody
    public CommentResp getAllPodsTemplates() {
        List<PodTemplate> podTemplates = podTemplateMapper.selectList(null);
        return new CommentResp(true, podTemplates, "");
    }

    @ResponseBody
    @SneakyThrows
    @RequestMapping("/addVirtual")
    public CommentResp addVirtual(@RequestParam("ImgName") String ImgName, @RequestParam("name") String name,
                                  @RequestParam("memory") int memory, @RequestParam("cpuNum") int cpuNum,
                                  @RequestParam("OStype") String OStype,@RequestParam("nettype") String NetType,
                                  @RequestParam("serverip") String serverip) throws InterruptedException {
        StringBuilder response = RemoteVMUtils.httputil(
                "http://" + serverip + ":8080/addVirtual?ImgName="+ImgName
                        +"&name="+name+"&memory="+memory+"&cpuNum="+cpuNum+"&OStype="
                        +OStype+"&nettype="+NetType+"&serverip="+serverip);
        ObjectMapper mapper1 = new ObjectMapper();
    // 处理响应
        return mapper1.readValue(response.toString(), CommentResp.class);
    }

    @ResponseBody
    @PostMapping("/addPodTemplate")
    public CommentResp addPodTemplate(@RequestBody PodTemplateInfo podTemplateInfo) {
        PodTemplate podTemplate = new PodTemplate();
        podTemplate.setName(podTemplateInfo.getName());
        podTemplate.setPodNamespace(podTemplateInfo.getPodNamespace());
        podTemplate.setPvcName(podTemplateInfo.getPvcName());
        podTemplate.setContainerInfoList(podTemplateInfo.getContainerInfoList());
        podTemplateMapper.insert(podTemplate);
        return new CommentResp(true, "", "");
    }
}

