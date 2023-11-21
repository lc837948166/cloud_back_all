package com.xw.cloud.bean;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 內存相关信息
 *
 */
@Data
@Getter
@ToString
public class MemoryInfo {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

}

