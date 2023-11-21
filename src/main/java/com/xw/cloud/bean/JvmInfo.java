package com.xw.cloud.bean;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * JVM相关信息
 *
 */
@Data
@Getter
@ToString
public class JvmInfo
{
    /**
     * 当前JVM占用的内存总数(G)
     */
    private double total;

    /**
     * JVM最大可内存总数(G)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

}
