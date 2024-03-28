package com.xw.cloud.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "T_CLOUD_POD_TEMPLATE")
@ApiModel(description = "容器模板信息")
public class PodTemplate {

    @TableId(type = IdType.AUTO)
    @JsonProperty(value = "id")
    @ApiModelProperty(value = "模板ID")
    private int id;

    @JsonProperty(value = "name")
    @ApiModelProperty(value = "模板名称")
    private String name;

    @JsonProperty(value = "podNamespace")
    @ApiModelProperty(value = "命名空间")
    private String podNamespace;

    @JsonProperty(value = "pvcName")
    @ApiModelProperty(value = "持久卷")
    private String pvcName;

    @ApiModelProperty(value = "容器信息列表")
    private String containerInfoList;
}
