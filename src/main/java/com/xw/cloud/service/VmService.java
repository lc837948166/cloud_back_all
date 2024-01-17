package com.xw.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.cloud.bean.PvInfo;
import com.xw.cloud.bean.VMInfo2;

import java.util.List;

public interface VmService extends IService<VMInfo2> {
    /**
     * 查询虚拟机经纬度
     * @param ip
     * @return
     */
    List<Double> queryLatAndLon(String ip);
}
