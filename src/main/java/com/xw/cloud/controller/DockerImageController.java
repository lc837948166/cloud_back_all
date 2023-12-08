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


//    private final String apiUrl = "http://39.98.124.97:8081/api/ssh";

    @GetMapping("/imageList")
    @ApiOperation("获取 Docker 镜像列表")
    public ResponseEntity<List<String>> listImages(@RequestParam("vmName") String vmName,
                                                   @RequestParam("endIp") String endIp) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        String userName1 = "root";
        String userPassword1 = "Upc123456@";
        String host1 = "39.101.136.242";

        String url = "http://" + endIp +":8081/api/ssh";
        String command = "docker image ls";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", host1);
        requestBody.put("username", userName1);
        requestBody.put("password", userPassword1);
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

        System.out.println("111");
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
    }


    @PostMapping("/uploadAndImport")
    @ApiOperation("上传并导入 Docker 镜像")
    public ResponseEntity<String> uploadAndImportImage(@RequestParam("imageFile") MultipartFile imageFile,
                                                       @RequestParam("vmName") String vmName,
                                                       @RequestParam("targetPath") String targetPath,
                                                       @RequestParam("endIp") String endIp) {

        QueryWrapper<VMInfo2> qw = new QueryWrapper<>();
        if (vmName != null && !vmName.equals("")) {
            qw.eq("name", vmName);
        }
        VMInfo2 vmInfo2 = vmService.getOne(qw);
        String userName = vmInfo2.getUsername();
        String userPassword = vmInfo2.getPasswd();
        String host = vmInfo2.getIp();

        String url = "http://" + endIp +":8081/api/ssh";
        String imagePath = "/etc/usr/xwfiles/";
        String filePath = imagePath + imageFile.getOriginalFilename();
        String mkdirCommand = "mkdir -p " + targetPath;
        String transCommand = "sshpass -p " + userPassword + " scp -o ConnectTimeout=3 -o StrictHostKeyChecking=no " + filePath + " " + userName + "@" + host + ":" + targetPath;

        System.out.println(imagePath);
        System.out.println(transCommand);

        //加载镜像
        String uploadCommand = "docker load -i " + filePath;
        System.out.println(uploadCommand);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", host);
        requestBody.put("username", userName);
        requestBody.put("password", userPassword);
        List<String> commands = Arrays.asList(
                mkdirCommand,
                transCommand,
                uploadCommand
        );
        requestBody.put("commands", commands);


        // 发起请求
        ResponseEntity<String> response = new RestTemplate().exchange(
                url,
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
            return ResponseEntity.ok("1");
        } else {
            // 处理请求失败情况
            return ResponseEntity.ok("1");
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

        String url = "http://" + host +":8081/api/ssh";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", host);
        requestBody.put("username", userName);
        requestBody.put("password", userPassword);
        List<String> commands = Arrays.asList(
                command
        );
        requestBody.put("commands", commands);


        // 发起请求
        ResponseEntity<String> response = new RestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
        );

        // 获取响应
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();

            return ResponseEntity.ok(responseBody);
        } else {
            // 处理请求失败情况
            return ResponseEntity.ok("fail");
        }


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


        String url = "http://" + host +":8081/api/ssh";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", host);
        requestBody.put("username", userName);
        requestBody.put("password", userPassword);
        List<String> commands = Arrays.asList(
                command
        );
        requestBody.put("commands", commands);


        // 发起请求
        ResponseEntity<String> response = new RestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
        );

        // 获取响应
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();

            return ResponseEntity.ok(responseBody);
        } else {
            // 处理请求失败情况
            return ResponseEntity.ok("删除失败");
        }

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
        String url = "http://" + host +":8081/api/ssh";
        String command = "docker ps -a ";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", host);
        requestBody.put("username", userName);
        requestBody.put("password", userPassword);
        List<String> commands = Arrays.asList(
                command
        );
        requestBody.put("commands", commands);


        // 发起请求
        ResponseEntity<String> response = new RestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
        );

        // 获取响应
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            // 处理响应字符串
            // 将输出结果按行分割，并返回镜像名称列表
            List<String> containers = Arrays.asList(responseBody.split("\n"));
            return ResponseEntity.ok(containers);
        } else {
            // 处理请求失败情况
            return ResponseEntity.ok(Collections.emptyList());
        }
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


        String url = "http://" + host +":8081/api/ssh";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", host);
        requestBody.put("username", userName);
        requestBody.put("password", userPassword);
        List<String> commands = Arrays.asList(
                command
        );
        requestBody.put("commands", commands);


        // 发起请求
        ResponseEntity<String> response = new RestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
        );

        // 获取响应
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();

            return ResponseEntity.ok(responseBody);
        } else {
            // 处理请求失败情况
            return ResponseEntity.ok("fail");
        }
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
