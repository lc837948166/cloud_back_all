package com.xw.cloud.controller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.bean.ImgFile;
import com.xw.cloud.service.ImagesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/Images")
public class ImagesController {

    @Resource(name = "imagesService")
    private ImagesService imagesService;


    @ResponseBody
    @RequestMapping("/imgList")
    public CommentResp imgList() {
        List<ImgFile> imgList = imagesService.getImgList();
        System.out.println(imgList);
        return new CommentResp(true, imgList,"");
    }

    @ResponseBody
    @RequestMapping("/addImg")  //前端要用post请求
    public CommentResp addImg(@RequestPart("file") MultipartFile file) {
        boolean result=imagesService.addImgFile(file.getOriginalFilename(),file);
        if(result)return new CommentResp(true, null,"添加镜像成功");
        else return new CommentResp(true, null,"添加镜像失败,与其他镜像名重复！");
    }
    @ResponseBody
    @RequestMapping("/deleteImg")
    public CommentResp deleteImg(@RequestParam("name") String name) {
        boolean result=imagesService.deleteImgFile(name);
        if(result)return new CommentResp(true, null,"删除成功");
        else return new CommentResp(true, null,"删除失败");
    }

    @ResponseBody
    @RequestMapping("/downImg")
    public CommentResp downImg(@RequestParam("name") String name, HttpServletResponse response) {
        boolean result=imagesService.downImgFile(name,response);
        if(result)return new CommentResp(true, null,"下载成功");
        else return new CommentResp(true, null,"下载失败");
    }


    @ResponseBody
    @RequestMapping(value = "/1addImg", method = RequestMethod.GET)
    public CommentResp cloneFromTemplate(@RequestParam("1name") String name,
                                         @RequestParam("clone_name") String clone_name) throws Exception {
        String virtualMachineIp = "127.0.0.1";
        String username = "root";
        String password = "111";

        Session session = null;
        ModelAndView modelAndView = new ModelAndView("jsonView");
        JSch jsch = new JSch();
        session = jsch.getSession(username, virtualMachineIp, 22);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();
        // 执行命令
        Channel execChannel = session.openChannel("exec");
        ((ChannelExec) execChannel).setCommand("virt-clone -o "+name+" -n "+clone_name+" --auto-clone"); // 设置执行的命令
        InputStream in = null;
        in = execChannel.getInputStream();  // 获取命令执行结果的输入流
        execChannel.connect();  // 连接远程执行命令
        byte[] tmp = new byte[1024];
        StringBuilder commandOutput = new StringBuilder(); //存储命令执行的输出
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
        String result=commandOutput.toString();
        if(result.contains("功")){
            return new CommentResp(true, null,"克隆成功");
        }
        else return new CommentResp(true, null,"克隆虚拟机失败，请检查是否与其他虚拟机名称重复");
    }

}

