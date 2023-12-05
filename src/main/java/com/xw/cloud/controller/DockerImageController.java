package com.xw.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcraft.jsch.*;
import com.xw.cloud.Utils.SshClient;
import com.xw.cloud.bean.VMInfo2;
import com.xw.cloud.service.impl.VmServiceImpl;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/docker")
@Api(tags = "Docker 镜像管理", description = "管理 Docker 容器中的镜像")
public class DockerImageController {

    @Autowired
    VmServiceImpl vmService;


    private final String apiUrl = "http://39.98.124.97:8081/api/ssh";

    @GetMapping("/imageList")
    @ApiOperation("获取 Docker 镜像列表")
    public ResponseEntity<List<String>> listImages(@RequestParam("vmName") String vmName) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        String command = "docker image ls";

        SshClient sshClient = new SshClient();
        String output = sshClient.runCommand(host, userName, userPassword, command);
        List<String> images = Arrays.asList(output.split("\n"));

        for (String list : images) {
            System.out.println(list);
        }
        return ResponseEntity.ok(images);

        /*
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", host);
        requestBody.put("username", userName);
        requestBody.put("password", userPassword);
        List<String> commands = Arrays.asList(
                "docker image ls"
        );
        requestBody.put("commands", commands);


        // 发起请求
        ResponseEntity<String> response = new RestTemplate().exchange(
                apiUrl,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
        );


        // 获取响应
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            // 处理响应字符串
            // 将输出结果按行分割，并返回镜像名称列表
            List<String> images = Arrays.asList(responseBody.split("\n"));
            return ResponseEntity.ok(images);
        } else {
            // 处理请求失败情况
            return ResponseEntity.ok(Collections.emptyList());
        }

        */
    }


    @PostMapping("/uploadAndImport")
    @ApiOperation("上传并导入 Docker 镜像")
    public ResponseEntity<String> uploadAndImportImage(@RequestParam("imageFile") MultipartFile imageFile,
                                                       @RequestParam("vmName") String vmName) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        Session session = null;
        Channel channel = null;


        try {
            JSch jsch = new JSch();
            session = jsch.getSession(userName, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(userPassword);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            String imagePath = "/opt/images";

            // 检查目标文件夹是否存在，如果不存在则创建
            try {
                sftpChannel.ls(imagePath); // 尝试列出目录
            } catch (SftpException e) {
                if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    String[] dirs = imagePath.split("/");
                    String path = "";
                    for (String dir : dirs) {
                        if (!dir.isEmpty()) {
                            path += "/" + dir;
                            try {
                                sftpChannel.ls(path); // 尝试列出目录
                            } catch (SftpException ex) {
                                if (ex.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                                    sftpChannel.mkdir(path); // 目录不存在，创建目录
                                }
                            }
                        }
                    }
                }
            }

            // 传输文件
            sftpChannel.put(imageFile.getInputStream(), imagePath+ "/" + imageFile.getOriginalFilename());

            System.out.println(imageFile.getOriginalFilename());

            String filePath = imagePath + "/" + imageFile.getOriginalFilename();
            String imageNameAndTag = "";

            //加载镜像
            String uploadCommand = "docker load -i " + filePath;
            String uploadOutput = runCommand(session, uploadCommand);

            System.out.println(uploadOutput);

            int startIndex = uploadOutput.lastIndexOf(" ") + 1;

            imageNameAndTag = uploadOutput.substring(startIndex);

            //提取镜像名称和标签
//            imageNameAndTag = uploadOutput.substring(uploadOutput.lastIndexOf("/") + 1, uploadOutput.lastIndexOf(":"));

            System.out.println(imageNameAndTag);

            //导入镜像
            String importCommand = "docker tag " + imageNameAndTag + " " + imageNameAndTag + "-imported " +
                    "&& docker rmi " + imageNameAndTag + " " +
                    "&& docker tag " + imageNameAndTag + "-imported " + imageNameAndTag;
            String importOutput = runCommand(session, importCommand);

            System.out.println("Imported image with name: " + imageNameAndTag);


            return ResponseEntity.ok(importOutput);
//            return "Image uploaded successfully";
        } catch (JSchException | SftpException e) {
            throw new RuntimeException("Failed to upload image", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }


    @PostMapping("/run")
    @ApiOperation("运行 Docker 容器")
    public ResponseEntity<String> runContainer(@RequestParam("imageName") String imageName, @RequestParam("vmName") String vmName) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        String command = "docker run -d " + imageName;

        SshClient sshClient = new SshClient();
        String output = sshClient.runCommand(host, userName, userPassword, command);


        return ResponseEntity.ok(output);
    }

    @GetMapping("/listContainer")
    @ApiOperation(" Docker 容器")
    public ResponseEntity<String> listContainer(
                                                @RequestParam("vmName") String vmName) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        System.out.println(userName+userPassword+host);

        String command = "docker ps -a ";
        String command1 = "docker image ls ";

        SshClient sshClient = new SshClient();
        String output = sshClient.runCommand(host, userName, userPassword, command);
        List<String> images = Arrays.asList(output.split("\n"));

        for (String list : images) {
            System.out.println(list);

        }
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/deleteImage")
    @ApiOperation("删除 Docker 镜像")
    public ResponseEntity<String> deleteImage(@RequestParam("imageName") String imageName,
                                              @RequestParam("vmName") String vmName) {
//                                              @RequestParam("containerId") String containerId) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        String command = "docker rmi " + imageName;

        SshClient sshClient = new SshClient();
        String output = sshClient.runCommand(host, userName, userPassword, command);

        /*
        // 先删除容器
        ResponseEntity<String> deleteContainerResponse = deleteContainer(containerId, vmName);

        if (deleteContainerResponse.getStatusCode().is2xxSuccessful()) {
            // 删除容器成功，再使用 Docker 命令行工具删除镜像
            String command = "docker rmi " + imageName;
            SshClient sshClient = new SshClient();
            String output = sshClient.runCommand(host, userName, userPassword, command);

            if (output.contains("Untagged")) {
                return ResponseEntity.ok("容器和镜像删除成功");
            } else {
                return ResponseEntity.ok("删除容器成功，但删除镜像失败");
            }
        } else {
            // 删除容器失败，直接返回错误响应
            return ResponseEntity.ok("删除容器失败");
        }*/
        return ResponseEntity.ok("删除镜像成功");

    }

    @GetMapping("/containerList")
    @ApiOperation("查询 Docker 容器列表")
    public ResponseEntity<List<String>> listContainers(@RequestParam("vmName") String vmName) {
        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        String command = "docker ps -a ";

        SshClient sshClient = new SshClient();
        String output = sshClient.runCommand(host, userName, userPassword, command);
        List<String> containers = Arrays.asList(output.split("\n"));

        for (String list : containers) {
            System.out.println(list);

        }
        return ResponseEntity.ok(containers);
    }


    @DeleteMapping("/deleteContainer")
    @ApiOperation("删除 Docker 容器")
    public ResponseEntity<String> deleteContainer(@RequestParam("containerId") String containerId,
                                                  @RequestParam("vmName") String vmName) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        String command = "docker rm " + containerId;

        SshClient sshClient = new SshClient();
        String output = sshClient.runCommand(host, userName, userPassword, command);

        return ResponseEntity.ok(output);
    }

    // 执行远程命令
    private static String runCommand(Session session, String command) throws JSchException, IOException {
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();
        channel.connect();

        StringBuilder outputBuffer = new StringBuilder();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                outputBuffer.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        channel.disconnect();
        return outputBuffer.toString();
    }

}
