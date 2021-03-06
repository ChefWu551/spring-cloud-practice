package com.wuyuefeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.model.Order;
import com.wuyuefeng.model.ResponseMsg;
import com.wuyuefeng.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("getOrder/{id}")
    public JSONObject getOrder(@PathVariable Integer id) {
        Order order = orderService.getOrder(id);
        return ResponseMsg.success(order);
    }

    @PostMapping("createOrder")
    public JSONObject createOrder(@RequestBody Order order) {
        Integer isSuccess = orderService.createOrder(order);
        if (isSuccess == 1) return ResponseMsg.success("插入成功");
        return ResponseMsg.fail();
    }

    @GetMapping("slethTest")
    public String getSleuthString() {
        return orderService.getSleuthString();
    }
}
