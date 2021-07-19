package com.wuyuefeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.model.PayAccount;
import com.wuyuefeng.model.ResponseMsg;
import com.wuyuefeng.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pay")
@Slf4j
public class PayController {

    @Autowired
    PayService payService;

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public JSONObject test() {
        return ResponseMsg.success("test success");
    }

    @GetMapping("account/{id}")
    public JSONObject getAccountInfo(@PathVariable Integer id) {
        log.info("端口号是：" + port + "; 执行服务1");
        PayAccount account = payService.getAccountInfo(id);
        return ResponseMsg.success(account);
    }

    @GetMapping ("money")
    public JSONObject payMoney(Integer accountId, Long money) {
        log.info("执行端口号：" + port + "；是服务1");
        return payService.pay(accountId, money);
    }
}
