package com.xw.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import okhttp3.Call;
import okhttp3.Response;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;

import io.kubernetes.client.apis.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
@CrossOrigin
@Controller
@Api(tags = "Kubernetes 集群概览", description = "提供 Kubernetes 集群的不同资源的概览信息")
public class DefaultController {
  @Value("${k8s.config}")
  private String k8sConfig;


  @ApiOperation(value = "查看概览页面", notes = "返回集群概览页面的路径")
  @RequestMapping(value = "/overview", method = RequestMethod.GET)
  public String overview(){
    return "overview";
  }



  @ApiOperation(value = "加载集群概览数据", notes = "获取集群中各种资源的概览信息")
  @ApiResponses({
          @ApiResponse(code = 200, message = "成功加载概览数据"),
          @ApiResponse(code = 500, message = "服务器错误")
  })
  @RequestMapping(value = "/overview/load", method = RequestMethod.GET)
  public ModelAndView overviewLoad() throws IOException, ApiException {
    ModelAndView modelAndView = new ModelAndView("jsonView");

    String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
    ApiClient client =
            ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
    Configuration.setDefaultApiClient(client);

    AppsV1Api api = new AppsV1Api();
    BatchV1beta1Api beta1Api = new BatchV1beta1Api();
    BatchV1Api dV1Api = new BatchV1Api();
    CoreV1Api coreV1Api = new CoreV1Api();
    NetworkingV1Api exV1Api = new NetworkingV1Api();

    NetworkingV1beta1Api exbetaV1Api =new NetworkingV1beta1Api();
    Map<String, Call> calls = new HashMap<>();


    calls.put("cronjob", (Call) beta1Api.listCronJobForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("daemonset", (Call) api.listDaemonSetForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("deployment", (Call) api.listDeploymentForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("job", (Call) dV1Api.listJobForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("replicaset", (Call) api.listReplicaSetForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("replication", (Call) coreV1Api.listReplicationControllerForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("statefulset", (Call) api.listStatefulSetForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("pod", (Call) coreV1Api.listPodForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("service", (Call) coreV1Api.listServiceForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("ingress", (Call) exbetaV1Api.listIngressForAllNamespacesCall(null,null, null, null, null, null, 5,  null,null,null));
    calls.put("configmap", (Call) coreV1Api.listConfigMapForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("pvc", (Call) coreV1Api.listPersistentVolumeClaimForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    calls.put("secret", (Call) coreV1Api.listSecretForAllNamespacesCall(null,null, null, null, null, null, 5, null, null, null));
    System.out.print("123123");
    for(Map.Entry<String, Call> entry : calls.entrySet()){
      Response response = entry.getValue().execute();

      if (!response.isSuccessful()) {
        modelAndView.addObject(entry.getKey(), "error!");

      }else{
        modelAndView.addObject(entry.getKey(),response.body().string());
      }
    }





    return modelAndView;
  }



}
