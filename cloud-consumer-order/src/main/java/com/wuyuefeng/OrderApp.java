package com.wuyuefeng;

import com.netflix.discovery.shared.Application;
import com.wuyuefeng.config.CustomerLoaderBalancerRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.wuyuefeng.dao")
@EnableEurekaClient
@RibbonClient(name = "cloud-order-server", configuration = CustomerLoaderBalancerRule.class)
public class OrderApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApp.class, args);
        applicationContext.getBean("");
    }

}
