package com.yuefeng.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@DefaultProperties(defaultFallback = "globalFallBackHandler")
public class HystrixService {

    // ----- 服务降级 ------
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

    // ---- 服务熔断 -----
    @HystrixCommand(fallbackMethod = "circuitBreakerHandler", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 开启断路器
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 异常情况超过最小阈值时，熔断器将会从关闭状态编程半开状态
        @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间范围
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(int id) {
        if (id < 0) throw new ArithmeticException("id不能是负数");

        String serialNum = UUID.randomUUID().toString();
        return Thread.currentThread().getName() + ". 调用成功，流水号： " + serialNum;
    }

    public String circuitBreakerHandler(int id) {
        return "服务目前熔断，正在恢复中.... 您输入的id： " + id;
    }
}
