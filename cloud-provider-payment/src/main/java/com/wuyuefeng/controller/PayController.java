package com.wuyuefeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.model.PayAccount;
import com.wuyuefeng.model.ResponseMsg;
import com.wuyuefeng.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pay")
public class PayController {

    @Autowired
    PayService payService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public JSONObject test() {
        return ResponseMsg.success("test success");
    }

    @GetMapping("account/{id}")
    public JSONObject getAccountInfo(@PathVariable Integer id) {
        PayAccount account = payService.getAccountInfo(id);
        return ResponseMsg.success(account);
    }
}
