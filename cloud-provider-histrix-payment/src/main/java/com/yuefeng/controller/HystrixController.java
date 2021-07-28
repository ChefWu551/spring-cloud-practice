package com.yuefeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.model.ResponseMsg;
import com.yuefeng.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hystrix")
public class HystrixController {

    @Autowired
    HystrixService hystrixService;

    @RequestMapping("test")
    public JSONObject test() throws InterruptedException {
        return ResponseMsg.success(hystrixService.timeSleep());
    }

    @RequestMapping("global/test")
    public JSONObject testGlobal() {
        return ResponseMsg.success(hystrixService.globalTimeOut());
    }

    @RequestMapping("fallDown/{id}")
    public JSONObject testFallDown(@PathVariable("id") Integer id) {
        return ResponseMsg.success(hystrixService.paymentCircuitBreaker(id));
    }

}
