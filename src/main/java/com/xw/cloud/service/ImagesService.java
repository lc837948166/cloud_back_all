package com.xw.cloud.service;


import com.xw.cloud.bean.ImgFile;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Log
@Service(value = "imagesService")
public class ImagesService {
    String home = System.getenv("HOME");

    public List<ImgFile> getImgList() {
        List<ImgFile> list = new ArrayList<>();
        File[] files = new File(home+"/images/").listFiles();
        if (files != null) {
            for (File file : files) {
                list.add(ImgFile.builder()
                        .name(file.getName())
                        .size(FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(file)))
                        .build());
            }
        }
        return list;
    }

    /**
     * 添加 img
     */

    public boolean checkImgList(String name) {
        File[] files = new File(home+"/images/").listFiles();
        if (files != null) {
            for (File file : files) {
                if(name==file.getName())return false;
            }
        }
        return true;
    }
    @SneakyThrows
    public Boolean addImgFile(String name, MultipartFile file) {
        if (!file.isEmpty()&&checkImgList(name)) {
            file.transferTo(new File(home+"/images/" + name ));
            log.info("文件" + name + "已经保存！");
            return true;
        }
        log.info("文件" + name + "保存失败！");
        return false;
    }

    /**
     * 下载 img
     */
    @SneakyThrows
    public Boolean downImgFile(String name, HttpServletResponse response) {
        File file = new File(home+"/images/" + name);
        if (!file.exists()) return false;
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));  // 设置编码格式
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        OutputStream os = response.getOutputStream();
        int i = 0;
        byte[] buff = new byte[1024];
        while ((i = bis.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
        bis.close();
        os.close();
        return true;
    }

    /**
     * 删除 img
     */
    public Boolean deleteImgFile(String name) {
        if (new File(home+"/images/" + name).delete()) {
            log.info("文件" + name + "已经删除！");
            return true;
        }
        log.info("文件" + name + "文件不存在");
        return false;
    }
}
