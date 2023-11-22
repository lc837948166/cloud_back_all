package com.xw.cloud.controller;

import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.bean.MachineInfo;
import com.xw.cloud.service.MachineService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Machine")
@Api(tags = {"test 接口 API"})
public class MachineController {
    @Resource
    private MachineService machineService;

    @GetMapping("/getMachineInfo")
//    @Operation(summary = "查询物理机信息", description = "用于查询物理机信息的接口")
    public CommentResp getMachineInfo() {
        return new CommentResp(true, machineService.getMachineInfo(), "");
    }
}


