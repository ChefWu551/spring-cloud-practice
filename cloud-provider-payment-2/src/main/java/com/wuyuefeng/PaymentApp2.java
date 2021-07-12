package com.wuyuefeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.wuyuefeng.dao")
@EnableEurekaClient
public class PaymentApp2 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApp2.class, args);
    }
}
