package com.xw.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.cloud.bean.NodeInfo;
import com.xw.cloud.bean.PvInfo;
import com.xw.cloud.bean.VMInfo2;
import com.xw.cloud.mapper.PvMapper;
import com.xw.cloud.mapper.VmMapper;
import com.xw.cloud.service.NodeService;
import com.xw.cloud.service.PvService;
import com.xw.cloud.service.VmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VmServiceImpl extends ServiceImpl<VmMapper, VMInfo2> implements VmService {

    @Autowired
    private NodeService nodeService;

    @Override
    public List<Double> queryLatAndLon(String ip) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("IP", ip);
        String serverIp = getOne(queryWrapper).getServerip();
        QueryWrapper queryWrapper1 = Wrappers.query();
        queryWrapper1.eq("NODEIP", serverIp);
        NodeInfo nodeInfo = nodeService.getOne(queryWrapper1);
        if(Objects.isNull(nodeInfo)) {
            throw new RuntimeException("虚拟机所在节点不存在或节点ip为内部ip，无法查询！");
        }
        Double nodeLon = nodeInfo.getNodeLon();
        Double nodeLat = nodeInfo.getNodeLat();
        List<Double> list = new ArrayList<>();
        list.add(nodeLon);
        list.add(nodeLat);
        return list;
    }
}
