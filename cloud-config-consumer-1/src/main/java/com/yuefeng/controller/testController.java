package com.yuefeng.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class testController {

    @Value("${config.info}")
    private String value;

    @RequestMapping("getConfigValue")
    public String getConfigValue() {
        return value;
    }
}
