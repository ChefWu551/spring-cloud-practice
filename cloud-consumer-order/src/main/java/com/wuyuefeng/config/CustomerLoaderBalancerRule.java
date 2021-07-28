package com.wuyuefeng.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CustomerLoaderBalancerRule {

//    @Bean
    public IRule initCustomerLB() {
//        随机的负载均衡
        return new RandomRule();
    }

}
