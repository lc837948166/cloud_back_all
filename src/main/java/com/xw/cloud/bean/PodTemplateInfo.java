package com.xw.cloud.bean;

import lombok.Data;

import java.util.List;

@Data
public class PodTemplateInfo {

    private String name;

    private String podNamespace;

    private String pvcName;

    private String containerInfoList;

}
