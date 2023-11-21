package com.xw.cloud.controller;

import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.bean.MachineInfo;
import com.xw.cloud.service.MachineService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Machine")
public class MachineController {
    @Resource
    private MachineService machineService;

    @GetMapping("/getMachineInfo")
    public CommentResp getMachineInfo() {
        return new CommentResp(true, machineService.getMachineInfo(), "");
    }
}


