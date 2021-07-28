package com.yuefeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yuefeng.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentFeignController {

    @Resource
    PaymentFeignService service;

    @GetMapping("/consumer/account/{id}")
    public JSONObject getAccountInfo(@PathVariable Integer id){
        return service.getAccountInfo(id);
    }

    @GetMapping("/consumer/pay/timeout")
    JSONObject timeoutTest() throws InterruptedException {

        return service.timeoutTest();
    }

    @GetMapping("/consumer/feign/fallback")
    @HystrixCommand(fallbackMethod = "timeSleepHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String timeSleep() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "线程池: " + Thread.currentThread().getName();
    }

    // 若请求超时，直接返回提示内容，以此达到服务降级的效果
    public String timeSleepHandler() {
        return "feign所在的服务器繁忙，请稍后再试";
    }
}
