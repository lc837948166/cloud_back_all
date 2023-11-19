package com.xw.cloud.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

//@Getter
//@ToString
//@Setter
//@AllArgsConstructor
@Data
@TableName(value = "VM_Template")
public class Template {
    @TableId(type = IdType.AUTO)
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "memory")
    private long memory;
    @JsonProperty(value = "cpuNum")
    private int cpuNum;
    @JsonProperty(value = "OStype")
    private String OStype;
}