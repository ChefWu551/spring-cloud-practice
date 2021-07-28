package com.yuefeng.service;

import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.model.ResponseMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class PaymentFeignFallBackService implements PaymentFeignService {

    @Override
    public JSONObject getAccountInfo(Integer id) {
        return ResponseMsg.fail("------PaymentFeignFallBackService 处理异常");
    }

    @Override
    public JSONObject timeoutTest() throws InterruptedException {
        return ResponseMsg.fail("------PaymentFeignFallBackService 处理异常");
    }
}
