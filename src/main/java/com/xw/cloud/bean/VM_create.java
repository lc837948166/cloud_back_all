package com.xw.cloud.bean;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

//@Getter
//@ToString
//@Setter
//@AllArgsConstructor
@Data
public class VM_create {
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "memory")
    private int memory;
    @JsonProperty(value = "cpuNum")
    private int cpuNum;
    @JsonProperty(value = "OStype")
    private String OStype;
}