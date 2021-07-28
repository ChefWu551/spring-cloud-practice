package com.yuefeng.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVER", fallback = PaymentFeignFallBackService.class)
public interface PaymentFeignService {

    @GetMapping("/pay/account/{id}")
    JSONObject getAccountInfo(@PathVariable("id") Integer id);

    @GetMapping("/pay/timeout")
    JSONObject timeoutTest() throws InterruptedException;

//    @GetMapping ("money")
//    JSONObject payMoney(Integer accountId, Long money);
}
