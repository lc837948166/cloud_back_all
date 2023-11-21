package com.xw.cloud.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@ToString
public class Virtual {
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "state")
    private String state;
    @JsonProperty(value = "maxMem")
    private String maxMem;
    @JsonProperty(value = "useMem")
    private String useMem;
    @JsonProperty(value = "cpuNum")
    private String cpuNum;
    @JsonProperty(value = "cpuTime")
    private String cpuTime;
}
