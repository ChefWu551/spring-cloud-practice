package com.wuyuefeng;

import com.wuyuefeng.config.CustomerLoaderBalancerRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@MapperScan("com.wuyuefeng.dao")
@EnableEurekaClient
//@RibbonClient(name = "cloud-order-server", configuration = CustomerLoaderBalancerRule.class)
public class OrderApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }

}
