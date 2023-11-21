package com.xw.cloud.bean;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 系统相关信息
 *
 */
@Data
@Getter
@ToString
public class SystemDetails {
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
    /**
     * 系统版本
     */
    private String osVersion;
    /**
     * 架构
     */
    private String osBuild;

}