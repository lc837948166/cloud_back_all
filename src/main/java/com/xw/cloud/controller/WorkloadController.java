package com.xw.cloud.controller;

import com.xw.cloud.inter.OperationLogDesc;
import io.kubernetes.client.openapi.models.V1DeleteOptions;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Yaml;
import io.kubernetes.client.openapi.models.V1ObjectMeta;

import okhttp3.*;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.apis.BatchV1beta1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;

import io.kubernetes.client.util.KubeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.InputStream;

import okhttp3.Response;
import com.xw.cloud.bean.ContainerInfo;
import com.xw.cloud.bean.PodInfo;
import com.xw.cloud.bean.PvcInfo;
import com.xw.cloud.bean.RequestInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Controller
@CrossOrigin
@RequestMapping("workload")
public class WorkloadController {
    @Value("${k8s.config}")
    private String k8sConfig;

    @RequestMapping(value = "cronjob", method = RequestMethod.GET)
    public String cronjob() {
        return "workload/cronjob";
    }

    @RequestMapping(value = "cronjob/list", method = RequestMethod.GET)
    public ModelAndView getCronjobList() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        BatchV1beta1Api api = new BatchV1beta1Api();

        Call call = api.listCronJobForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);


        Response response = call.execute();

        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }

        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    @RequestMapping(value = "/daemonset", method = RequestMethod.GET)
    public String daemonset() {
        return "workload/daemonset";
    }

    @RequestMapping(value = "/daemonset/list", method = RequestMethod.GET)
    public ModelAndView getDaemonsetList() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        AppsV1Api api = new AppsV1Api();

        Call call = api.listDaemonSetForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);


        Response response = call.execute();

        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }

        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    @RequestMapping(value = "/deployment", method = RequestMethod.GET)
    public String deployment() {
        return "workload/deployment";
    }

    @RequestMapping(value = "/deployment/list", method = RequestMethod.GET)
    public ModelAndView getDeploymentList() throws IOException, ApiException {


        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        AppsV1Api api = new AppsV1Api();

        Call call = api.listDeploymentForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);


        Response response = call.execute();
        System.out.print(response);
        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }

        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    @RequestMapping(value = "/createDeployment", method = RequestMethod.GET)
    public String createDeployment() {
        return "workload/createDeployment";
    }

    @RequestMapping(value = "/createDeployment", method = RequestMethod.POST)
    @ResponseBody
    public String createDeployment(@RequestParam("yamlFile") MultipartFile yamlFile) throws IOException, ApiException {
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        AppsV1Api appsApi = new AppsV1Api();

        String yamlContent = new String(yamlFile.getBytes(), StandardCharsets.UTF_8);
        Object obj = Yaml.load(yamlContent);


        if (obj instanceof V1Pod) {
            System.out.println("V1Pod");
            V1Pod pod = (V1Pod) obj;
            V1ObjectMeta metadata = pod.getMetadata();
            if (metadata != null) {
                String kind = pod.getKind();
                String namespace = metadata.getNamespace() != null ? metadata.getNamespace() : "default";
                switch (kind) {
                    case "Pod":
                        api.createNamespacedPod(namespace, pod, null, null, null);
                        break;
                    case "Deployment":
                        System.out.println("deployment类型qqqqq");
                        // 处理 Deployment 类型
                        appsApi.createNamespacedDeployment(namespace, (V1Deployment) obj, null, null, null);
                        break;
                    // ... 其他处理逻辑
                    // 添加其他资源类型的处理逻辑
                    default:
                        throw new IllegalArgumentException("Unknown resource type: " + kind);
                }
            }
        } else if (obj instanceof V1Deployment) {
            System.out.println("deployment类型");
            // 处理 Deployment 类型
            V1Deployment deployment = (V1Deployment) obj;
            V1ObjectMeta metadata = deployment.getMetadata();
            if (metadata != null) {
                String kind = deployment.getKind();
                String namespace = metadata.getNamespace() != null ? metadata.getNamespace() : "default";
                switch (kind) {
                    case "Pod":
//            api.createNamespacedPod(namespace, pod, null, null, null);
                        break;
                    case "Deployment":
                        appsApi.createNamespacedDeployment(namespace, deployment, null, null, null);
                        break;
                    // 处理其他资源类型的逻辑
                    default:
                        throw new IllegalArgumentException("Unknown resource type: " + kind);
                }
            }

        } else if (obj != null) {
            return "null";
        }

        return "Deployment created successfully.";
    }


    @RequestMapping(value = "/job", method = RequestMethod.GET)
    public String job() {
        return "workload/job";
    }

    @RequestMapping(value = "/job/list", method = RequestMethod.GET)
    public ModelAndView getJobList() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        BatchV1Api api = new BatchV1Api();

        Call call = api.listJobForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);


        Response response = call.execute();

        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }

        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    private void convertIntOrStringToString(V1PodList podList) {
        List<V1Pod> items = podList.getItems();
        for (V1Pod pod : items) {
            List<V1Container> containers = pod.getSpec().getContainers();
            for (V1Container container : containers) {
                V1Probe probe = container.getReadinessProbe();
                if (probe != null) {
                    io.kubernetes.client.openapi.models.V1HTTPGetAction httpGet = probe.getHttpGet();
                    if (httpGet != null) {
                        IntOrString port = httpGet.getPort();
                        if (port != null) {
                            if (port.getStrValue() == null && port.getIntValue() != null) {
                                // 将整数类型转换为字符串类型
                                String stringValue = String.valueOf(port.getIntValue());
                                httpGet.setPort(new IntOrString(stringValue));
                            }
                        }
                    }
                }
            }
        }
    }


//  @RequestMapping(value = "/pod", method = RequestMethod.GET)
//  public String pod(){
//    return "workload/pod";
//  }

    /**
     * 获取集群信息
     *
     * @return
     * @throws IOException
     * @throws ApiException
     */
    @RequestMapping(value = "/getNodeList", method = RequestMethod.GET)
    @OperationLogDesc(module = "容器管理", events = "获取节点")
    public ModelAndView getNode() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("workload/getPodList");

        // 通过流读取，方式1
        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
        // 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));
        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api();

        try {
            // 获取集群节点信息
            V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, null, null);

            // 处理节点信息
            for (V1Node node : nodeList.getItems()) {
                System.out.println("Node Name: " + node.getMetadata().getName());
                // 可以进一步处理其他节点信息，比如标签、状态等
            }

            // 获取集群节点信息
            Call call = api.listPodForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);
            Response response = call.execute();

            // 处理第二次请求的响应
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // 处理响应体，并将其添加到 ModelAndView 中
                modelAndView.addObject("result", responseBody);
            } else {
                // 处理请求失败情况
                modelAndView.addObject("result", "Error: " + response.message());
            }

        } catch (ApiException e) {
            // 处理异常情况
            System.err.println("Exception when calling CoreV1Api#listNode");
            e.printStackTrace();
        }

        return modelAndView;

    }

    /**
     * 获取pod列表
     *
     * @return
     * @throws IOException
     * @throws ApiException
     */
    @RequestMapping(value = "/getPodList", method = RequestMethod.GET)
    @OperationLogDesc(module = "容器管理", events = "获取容器列表")
    public ModelAndView getPod() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");

        // 通过流读取，方式1
        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
        // 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));
        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api();


        V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);

        for (V1Pod pod : podList.getItems()) {
            if (pod.getMetadata().getAnnotations() == null || !pod.getMetadata().getAnnotations().containsKey("status")) {
                pod.getMetadata().setAnnotations(new HashMap<>());
                pod.getMetadata().getAnnotations().put("status", "Yes");
                api.replaceNamespacedPod(pod.getMetadata().getName(), pod.getMetadata().getNamespace(), pod, null, null, null);
            }

        }

        // 发起第二次请求并等待请求完成
        Call call = api.listPodForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);
        Response response = call.execute();

        // 处理第二次请求的响应
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            // 处理响应体，并将其添加到 ModelAndView 中
            modelAndView.addObject("result", responseBody);
        } else {
            // 处理请求失败情况
            modelAndView.addObject("result", "Error: " + response.message());
        }


//        modelAndView.addObject("podList", podList.getItems());


        return modelAndView;
    }

//  @RequestMapping(value = "/getPodList", method = RequestMethod.GET)
//  public String Test(Model model) throws IOException, ApiException{
//    // 通过流读取，方式1
//    InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
//    // 使用 InputStream 和 InputStreamReader 读取配置文件
//    KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));
//    ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();
//    Configuration.setDefaultApiClient(client);
//    CoreV1Api api = new CoreV1Api();
//
//
//    V1PodList podList = api.listPodForAllNamespaces(null,null, null, null, null, null, null, null, null,null );
//
//    for (V1Pod pod : podList.getItems()) {
//      if (pod.getMetadata().getAnnotations() == null || !pod.getMetadata().getAnnotations().containsKey("status")) {
//        pod.getMetadata().setAnnotations(new HashMap<>());
//        pod.getMetadata().getAnnotations().put("status", "Yes");
//      }
//
//    }
//
//    model.addAttribute("podList", podList.getItems());
//    return "workload/getPodList";
//  }


    /**
     * 迁移pod
     *
     * @param podName
     * @param podNamespace
     * @param model
     * @return
     */
    @RequestMapping(value = "/editPod", method = RequestMethod.GET)
    public String editPod(@RequestParam("podName") String podName,
                          @RequestParam("podNamespace") String podNamespace,
                          Model model) {

        // 将接收到的值添加到 model 中
        model.addAttribute("podName", podName);
        model.addAttribute("podNamespace", podNamespace);
        return "workload/editPod";
    }

    /**
     * 通过pod名、命名空间，新的节点名迁移镜像（前端给新的节点名）
     *
     * @param podinfo
     * @return
     * @throws IOException
     * @throws ApiException
     */
    @RequestMapping(value = "/editPod", method = RequestMethod.POST)
    @ResponseBody
//    public String editPod(@RequestParam("podName") String podName,
//                          @RequestParam("podNamespace") String podNamespace) throws IOException, ApiException {
    @OperationLogDesc(module = "容器管理", events = "迁移容器")
    public String editPod(@RequestBody PodInfo podinfo) throws IOException, ApiException {
        String podName = podinfo.getPodName();
        String podNamespace = podinfo.getPodNamespace();
        String newNodeName = podinfo.getPodNodeName();

        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
// 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));
//    String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        try {
            // 获取Pod对象并确定其当前节点名称
            V1Pod pod = api.readNamespacedPod(podName, podNamespace, null);


            // 复制 Pod 对象
            V1Pod newPod = new V1Pod();
            newPod.setMetadata(new V1ObjectMeta());
            newPod.setSpec(new V1PodSpec());
            newPod.setStatus(new V1PodStatus());

            System.out.println(pod.getMetadata().getName());

            newPod.getMetadata().setName(pod.getMetadata().getName());
            newPod.getMetadata().setNamespace(pod.getMetadata().getNamespace());
            newPod.getMetadata().setLabels(pod.getMetadata().getLabels());
            newPod.getMetadata().setAnnotations(pod.getMetadata().getAnnotations());
            newPod.getSpec().setContainers(pod.getSpec().getContainers());
            newPod.getSpec().setVolumes(pod.getSpec().getVolumes());
            newPod.getSpec().setNodeName(newNodeName);
            newPod.setStatus(pod.getStatus());

            System.out.println("ready to delete...");

            // 删除当前 Pod
            V1DeleteOptions deleteOptions = new V1DeleteOptions();
            deleteOptions.setPropagationPolicy("Foreground");
            api.deleteNamespacedPod(podName, podNamespace, null, null, null, null, null, deleteOptions);

            System.out.println("delete successfully");

            // 在新节点上创建 Pod

            Thread.sleep(5000);

//      System.out.println(newPod.getSpec().getNodeName());
//      System.out.println(newPod);
            api.createNamespacedPod(podNamespace, newPod, null, null, null);

            return "Successfully moved Pod ";
        } catch (ApiException e) {
            if (e.getCode() == 409) {
                // 发生冲突，返回失败响应给前端
                return "Error: Pod creation failed due to conflict";
//        return "错误: 重复创建！";
            } else {
                // 其他错误，返回失败响应给前端
                return "Error: Failed to create Pod";
//        return "错误: 创建失败！";
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过pod名、命名空间以及新的容器信息配置镜像
     *
     * @param podinfo
     * @return
     * @throws IOException
     * @throws ApiException
     */
    @RequestMapping(value = "/configureImage", method = RequestMethod.POST)
    @ResponseBody
//    public String configureImage(@RequestParam("podName") String podName,
//                                 @RequestParam("podNamespace") String podNamespace,
//                                 @RequestParam("containerName") String containerName,
//                                 @RequestParam("imageName") String imageName) throws IOException, ApiException {

    @OperationLogDesc(module = "容器管理", events = "配置镜像")
    public String configureImage(@RequestBody PodInfo podinfo) throws IOException, ApiException {
        String podName = podinfo.getPodName();
        String podNamespace = podinfo.getPodNamespace();
        List<ContainerInfo> containerInfoList = podinfo.getContainerInfoList();

        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
// 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));
//    String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        try {
            // 获取Pod对象并确定其当前节点名称
            V1Pod pod = api.readNamespacedPod(podName, podNamespace, null);


            // 新建 Pod 对象
            V1Pod newPod = new V1Pod();
            newPod.setMetadata(new V1ObjectMeta());
            newPod.setSpec(new V1PodSpec());
            newPod.setStatus(new V1PodStatus());

            // 复制 Pod 对象并添加container以配置image
            newPod.getMetadata().setName(pod.getMetadata().getName());
            newPod.getMetadata().setNamespace(pod.getMetadata().getNamespace());
            newPod.getMetadata().setLabels(pod.getMetadata().getLabels());
            newPod.getMetadata().setAnnotations(pod.getMetadata().getAnnotations());
            newPod.getSpec().setContainers(pod.getSpec().getContainers());
            newPod.getSpec().setVolumes(pod.getSpec().getVolumes());
            newPod.setStatus(pod.getStatus());


            for (ContainerInfo containerInfo : containerInfoList) {
                String containerName = containerInfo.getContainerName();
                String containerImage = containerInfo.getContainerImage();
                int port = containerInfo.getPort();

                // 处理每个 containerInfo 对象
                V1Container newContainer = new V1Container()
                        .name(containerName)
                        .image(containerImage)
                        .ports(Collections.singletonList(
                                new V1ContainerPort()
                                        .containerPort(port)));
                newPod.getSpec().getContainers().add(newContainer);
            }



            System.out.println(pod.getMetadata().getName());



            // 删除当前 Pod
            V1DeleteOptions deleteOptions = new V1DeleteOptions();
            deleteOptions.setPropagationPolicy("Foreground");
            api.deleteNamespacedPod(podName, podNamespace, null, null, null, null, null, deleteOptions);

            // 在新节点上创建 Pod

            Thread.sleep(5000);

//      System.out.println(newPod);
            api.createNamespacedPod(podNamespace, newPod, null, null, null);

            return "Successfully configured Image ";
        } catch (ApiException e) {
            if (e.getCode() == 409) {
                // 发生冲突，返回失败响应给前端
                return "Error: Pod creation failed due to conflict";
//        return "错误: 重复创建！";
            } else {
                // 其他错误，返回失败响应给前端
                return "Error: Failed to configure Image";
//        return "错误: 创建失败！";
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 通过pod名、命名空间、节点名和容器信息创建pod
     * @return
     */
    @RequestMapping(value = "/createPod", method = RequestMethod.GET)

    public String createPod() {
        return "workload/createPod";
    }

    @RequestMapping(value = "/createPod", method = RequestMethod.POST)
    @ResponseBody
    @OperationLogDesc(module = "容器管理", events = "创建容器")
    public String createPod(@RequestBody PodInfo podinfo) throws IOException, ApiException {

//
//        PodInfo podinfo = requestInfo.getPodInfo();
////        PvcInfo pvcInfo = requestInfo.getPvcInfo();
//        System.out.println("1222222222222222222222222");

        System.out.println(podinfo);
        String podName = podinfo.getPodName();
        String podNamespace = podinfo.getPodNamespace();
        String podNodeName = podinfo.getPodNodeName();
        List<ContainerInfo> containerInfoList = podinfo.getContainerInfoList();
//        String pvcName = pvcInfo.getPvcName();


        System.out.println(podNamespace);

        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
// 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));
//    String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        try {



            // 添加容器到Pod的规格中
            List<V1Container> containers = new ArrayList<>();

            for (ContainerInfo containerInfo : containerInfoList) {
                String containerName = containerInfo.getContainerName();
                String containerImage = containerInfo.getContainerImage();
                int port = containerInfo.getPort();

                port = 80;
                // 处理每个 containerInfo 对象...
                V1Container container = new V1Container()
                        .name(containerName)
                        .image(containerImage)
                        .ports(Collections.singletonList(
                                new V1ContainerPort()
                                        .containerPort(port)));
                containers.add(container);
            }



//            V1Container container1 = new V1Container()
//                    .name("test")
//                    .image("rancher/klipper-lb:v0.4.4")
//                    .ports(Collections.singletonList(
//                            new V1ContainerPort()
//                                    .containerPort(port)));

            // 将容器添加到容器列表中

//            containers.add(container1);


            // 创建 PVC
//            V1PersistentVolumeClaimVolumeSource pvcVolumeSource = new V1PersistentVolumeClaimVolumeSource();
//            pvcVolumeSource.setClaimName(pvcName);
//
//            V1Volume pvcVolume = new V1Volume();
//            pvcVolume.setName(pvcName);
//            pvcVolume.setPersistentVolumeClaim(pvcVolumeSource);

            //创建spec
            V1PodSpec podSpec = new V1PodSpec()
                    .nodeName(podNodeName)
//                    .volumes(Collections.singletonList(pvcVolume))
                    .containers(containers);

            //添加单个container
//      V1PodSpec podSpec = new V1PodSpec()
//              .containers(Collections.singletonList(container));

            //创建metadata
            V1ObjectMeta podMetadata = new V1ObjectMeta()
                    .namespace(podNamespace)
                    .name(podName);

            //创建pod
            V1Pod pod = new V1Pod()
                    .metadata(podMetadata)
                    .spec(podSpec);


            System.out.println("创建111111111111111");
            V1Pod createdPod = api.createNamespacedPod(podNamespace, pod, null, null, null);
            return "Pod created successfully.";

        } catch (ApiException e) {
            if (e.getCode() == 409) {
                // 发生冲突，返回失败响应给前端
                return "Error: Pod creation failed due to conflict";
//        return "错误: 重复创建！";
            } else {
                // 其他错误，返回失败响应给前端
                return e.getResponseBody();
//        return "错误: 创建失败！";
            }
        }


        //yaml文件创建
    /*String yamlContent = new String(yamlFile.getBytes(), StandardCharsets.UTF_8);
    Object obj = Yaml.load(yamlContent);



    if (obj instanceof V1Pod) {
      System.out.println("V1Pod");
      V1Pod pod = (V1Pod) obj;
      V1ObjectMeta metadata = pod.getMetadata();
      if (metadata != null) {
        String kind = pod.getKind();
        String namespace = metadata.getNamespace() != null ? metadata.getNamespace() : "default";
        switch (kind) {
          case "Pod":
            api.createNamespacedPod(namespace, pod, null, null, null);

            break;

          default:
            throw new IllegalArgumentException("Unknown resource type: " + kind);
        }
      }
    } else if (obj instanceof V1Deployment) {
      System.out.println("deployment类型");
    // 处理 Deployment 类型
      V1Deployment deployment = (V1Deployment) obj;
      V1ObjectMeta metadata = deployment.getMetadata();
      if (metadata != null) {
        String kind = deployment.getKind();
        String namespace = metadata.getNamespace() != null ? metadata.getNamespace() : "default";
        switch (kind) {
          case "Pod":
//            api.createNamespacedPod(namespace, pod, null, null, null);
            break;
          case "Deployment":
            appsApi.createNamespacedDeployment(namespace, deployment, null, null, null);
            break;
          // 处理其他资源类型的逻辑
          default:
            throw new IllegalArgumentException("Unknown resource type: " + kind);
        }
      }

  }else if (obj != null ){
      return "null";
    }
*/
//    return "Pod created successfully.";


//    return "Pod created successfully: " + createdPod.getMetadata().getName();
//    System.out.println("11111");
//    return "success";
    }

    @RequestMapping(value = "/pod", method = RequestMethod.GET)
    public String pod(Model model) throws IOException, ApiException {

        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);

        System.out.println("1234345");
//    model.addAttribute("podList", podList.getItems());
        model.addAttribute("podList", podList.getItems());


//    convertIntOrStringToString(podList);
//     使用 Jackson ObjectMapper 将 List 转换为 JSON 字符串
//    ObjectMapper objectMapper = new ObjectMapper();
//    String jsonString = objectMapper.writeValueAsString(podList);
//
//    modelAndView.addObject("result",jsonString);
        return "workload/pod";
    }

    /**
     * 通过pod名和命名空间删除pod
     *
     * @param podinfo
     * @return
     * @throws IOException
     * @throws ApiException
     */
    @RequestMapping(value = "/deletePod", method = RequestMethod.POST)
    @ResponseBody
//    public String deletePod(@RequestParam("podName") String podName, @RequestParam("podNamespace") String podNamespace) throws IOException, ApiException {
    @OperationLogDesc(module = "容器管理", events = "删除容器")
    public String deletePod(@RequestBody PodInfo podinfo) throws IOException, ApiException {

        String podName = podinfo.getPodName();
        String podNamespace = podinfo.getPodNamespace();
        System.out.println("11111");


        long startTime = System.currentTimeMillis();

        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
        // 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));

        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        AppsV1Api appsApi = new AppsV1Api();

        V1DeleteOptions deleteOptions = new V1DeleteOptions();
        deleteOptions.setPropagationPolicy("Foreground");
        try {
            api.deleteNamespacedPod(podName, podNamespace, null, null, null, null, null, deleteOptions);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            System.out.println("删除 Pod 操作的执行时间为：" + executionTime + " 毫秒");

            return "Pod deleted successfully: " + podName;
        } catch (ApiException e) {
            return "Failed to delete the Pod: " + podName + ", Error: " + e.getMessage();
        }
//    return "success";
    }

    /**
     * 通过pod名和命名空间停止pod
     *
     * @param podinfo
     * @return
     * @throws IOException
     * @throws ApiException
     */
    @RequestMapping(value = "/stopPod", method = RequestMethod.POST)
    @ResponseBody
//    public String stopPod(@RequestParam("podName") String podName, @RequestParam("podNamespace") String podNamespace) throws IOException, ApiException {
    @OperationLogDesc(module = "容器管理", events = "停止容器")
    public String stopPod(@RequestBody PodInfo podinfo) throws IOException, ApiException {

        String podName = podinfo.getPodName();
        String podNamespace = podinfo.getPodNamespace();

        long startTime = System.currentTimeMillis();

        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
        // 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));

        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        AppsV1Api appsApi = new AppsV1Api();

        try {
//      V1PodStatus newStatus = new V1PodStatus();
//      // 设置新的状态属性
//      newStatus.setPhase("Pending");
//      newStatus.setMessage("Pod is stopping successfully");


            // 获取Pod的当前状态
            V1Pod pod = api.readNamespacedPod(podName, podNamespace, null);


//      System.out.println("------------------------------------------");
//      System.out.println(pod.getMetadata().getName());
//      String podLogs = api.readNamespacedPodLog(podName, podNamespace, null, null,null, null, null, null, null, null, null);
//      System.out.println(podLogs);


//      pod.setStatus(newStatus);

            // 检查Annotations是否为null
            if (pod.getMetadata().getAnnotations() == null) {
                pod.getMetadata().setAnnotations(new HashMap<>());
            }
            System.out.println("到这里");
            // 修改Pod的状态为Stopped
            pod.getMetadata().getAnnotations().put("status", "No");
            api.replaceNamespacedPod(podName, podNamespace, pod, null, null, null);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            System.out.println("停止 Pod 操作的执行时间为：" + executionTime + " 毫秒");

            return "Pod stopped successfully: " + podName;
        } catch (ApiException e) {
            return "Failed to stop the Pod: " + podName + ", Error: " + e.getMessage();
        }
    }

    /**
     * 通过pod名和命名空间启动pod
     *
     * @param podinfo
     * @return
     * @throws IOException
     * @throws ApiException
     */
    @RequestMapping(value = "/startPod", method = RequestMethod.POST)
    @ResponseBody
    @OperationLogDesc(module = "容器管理", events = "启动容器")
//    public String startPod(@RequestParam("podName") String podName, @RequestParam("podNamespace") String podNamespace) throws IOException, ApiException {
    public String startPod(@RequestBody PodInfo podinfo) throws IOException, ApiException {

        String podName = podinfo.getPodName();
        String podNamespace = podinfo.getPodNamespace();
        System.out.println("333333");

        long startTime = System.currentTimeMillis();

        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
        // 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));

        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        AppsV1Api appsApi = new AppsV1Api();

        try {

            V1PodStatus newStatus = new V1PodStatus();
// 设置新的状态属性
            newStatus.setPhase("Running");
            newStatus.setMessage("Pod is running successfully");

            // 获取Pod的当前状态
            V1Pod pod = api.readNamespacedPod(podName, podNamespace, null);
            pod.setStatus(newStatus);

            if (pod.getMetadata().getAnnotations() == null) {
                pod.getMetadata().setAnnotations(new HashMap<>());
            }


            // 修改Pod的状态为Running
            pod.getMetadata().getAnnotations().put("status", "Yes");
            api.replaceNamespacedPod(podName, podNamespace, pod, null, null, null);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            System.out.println("启动 Pod 操作的执行时间为：" + executionTime + " 毫秒");

            return "Pod started successfully: " + podName;
        } catch (ApiException e) {
            return "Failed to start the Pod: " + podName + ", Error: " + e.getMessage();
        }
    }


    @RequestMapping(value = "/deleteDeployment", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDeployment(@RequestParam String deploymentName, @RequestParam String deploymentNamespace) throws IOException, ApiException {
//    String deploymentName = null;
//    String deploymentNamespace = podNamespace;

        InputStream in1 = this.getClass().getResourceAsStream("/k8s/config");
        // 使用 InputStream 和 InputStreamReader 读取配置文件
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(in1));

        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();

        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        AppsV1Api appsApi = new AppsV1Api();

        // 删除 Deployment 中的所有 Pods
        try {



            deploymentName = "test-deployment"; // 替换成你的命名空间
            deploymentNamespace = "default"; // 替换成你的 Deployment 名称
            V1DeleteOptions deleteOptions = new V1DeleteOptions();
            deleteOptions.setPropagationPolicy("Foreground");

            System.out.println(deploymentName);
            System.out.println(deploymentNamespace);


            appsApi.deleteNamespacedDeployment(
                                deploymentName,
                    deploymentNamespace,
                    null,
                    null,
                    null,
                    null,
                    null,
                    deleteOptions
                        );

            return "Deployment deleted successfully";
        } catch (ApiException e) {
            return "Exception when calling AppsV1Api#deleteNamespacedDeployment: " + e.getMessage();
        }
    }


    @RequestMapping(value = "/pod/list", method = RequestMethod.GET)
    public ModelAndView getPodList() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        Call call = api.listPodForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);

//    V1PodList podList = api.listPodForAllNamespaces(null,null, null, null, null, null, null, null, null,null );
//    modelAndView.addObject("result",podList.getItems());

        Response response = call.execute();

        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }


        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    @RequestMapping(value = "/replicaset", method = RequestMethod.GET)
    public String replicaset() {
        return "workload/replicaset";
    }

    @RequestMapping(value = "/replicaset/list", method = RequestMethod.GET)
    public ModelAndView getReplicasetList() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        AppsV1Api api = new AppsV1Api();

        Call call = api.listReplicaSetForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);


        Response response = call.execute();

        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }

        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    @RequestMapping(value = "/replication", method = RequestMethod.GET)
    public String replication() {
        return "/workload/replication";
    }

    @RequestMapping(value = "/replication/list", method = RequestMethod.GET)
    public ModelAndView getReplicationList() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        Call call = api.listReplicationControllerForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);


        Response response = call.execute();

        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }

        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    @RequestMapping(value = "/statefulset", method = RequestMethod.GET)
    public String statefulset() {
        return "workload/statefulset";
    }

    @RequestMapping(value = "/statefulset/list", method = RequestMethod.GET)
    public ModelAndView getStatefulsetList() throws IOException, ApiException {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        AppsV1Api api = new AppsV1Api();

        Call call = api.listStatefulSetForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);


        Response response = call.execute();

        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }

        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }

    private class V1HTTPGetAction {
    }
}
