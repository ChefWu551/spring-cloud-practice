package com.yuefeng.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@DefaultProperties(defaultFallback = "globalFallBackHandler")
public class HystrixService {

    @HystrixCommand(fallbackMethod = "timeSleepHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String timeSleep() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "线程池: " + Thread.currentThread().getName();
    }

    // 若请求超时，直接返回提示内容，以此达到服务降级的效果
    public String timeSleepHandler() {
        return "服务器繁忙，请稍后再试";
    }

    // 注解表明该方法自动使用默认的服务降级方法
    @HystrixCommand
    public String globalTimeOut() {
        int i = 10 /0;
        return "线程池: " + Thread.currentThread().getName();
    }

    // 有自定义的走自定义，无自定义直接走全局
    public String globalFallBackHandler() {
        return "服务器繁忙，请稍后再试，全局服务降级";
    }
}
