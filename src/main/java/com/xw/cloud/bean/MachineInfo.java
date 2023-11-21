package com.xw.cloud.bean;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 机器相关信息
 *
 */
@Data
@Builder
@Getter
@ToString
public class MachineInfo {
    public CpuInfo cpuInfo;
    public JvmInfo jvmInfo;
    public MemoryInfo memoryInfo;
    public SystemDetails systeminfo;
    public List<SysFile> sysFiles;
}
