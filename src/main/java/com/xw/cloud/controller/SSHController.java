package com.xw.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SSHController {
    @RequestMapping("/sshpage")
    public String webSSHpage(){
        return "webssh";
    }
}
