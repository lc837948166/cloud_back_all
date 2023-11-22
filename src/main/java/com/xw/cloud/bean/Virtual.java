package com.xw.cloud.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Builder
@Getter
@ToString
@ApiModel(description = "虚拟机信息")
public class Virtual {
    @ApiModelProperty(value = "虚拟机ID")
    private int id;

    @ApiModelProperty(value = "虚拟机名称")
    private String name;

    @ApiModelProperty(value = "虚拟机状态")
    private String state;
}