package com.xw.cloud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Snapshot {
    private String name;
    private String creationTime;
    private String state;
}
