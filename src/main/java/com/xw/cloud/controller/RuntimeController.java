package com.xw.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.management.ManagementFactory;

@RestController
public class RuntimeController {

    @GetMapping("/runtime")
    public String getRuntime() {
        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        long uptimeSeconds = uptimeMillis / 1000;
        long uptimeMinutes = uptimeSeconds / 60;
        long uptimeHours = uptimeMinutes / 60;
        long uptimeDays = uptimeHours / 24;

        return String.format("%d days, %d hours, %d minutes, %d seconds",
                uptimeDays, uptimeHours % 24, uptimeMinutes % 60, uptimeSeconds % 60);
    }
}
