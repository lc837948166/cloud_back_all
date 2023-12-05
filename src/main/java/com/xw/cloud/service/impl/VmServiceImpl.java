package com.xw.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.cloud.bean.PvInfo;
import com.xw.cloud.bean.VMInfo2;
import com.xw.cloud.mapper.PvMapper;
import com.xw.cloud.mapper.VmMapper;
import com.xw.cloud.service.PvService;
import com.xw.cloud.service.VmService;
import org.springframework.stereotype.Service;

@Service
public class VmServiceImpl extends ServiceImpl<VmMapper, VMInfo2> implements VmService {
}
