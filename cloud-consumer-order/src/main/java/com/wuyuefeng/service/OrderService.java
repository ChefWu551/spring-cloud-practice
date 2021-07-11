package com.wuyuefeng.service;

import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.dao.OrderDao;
import com.wuyuefeng.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    private static final String PAYMENT_URL = "http://localhost:8081/pay/money?accountId=1&money=";

    @Autowired
    RestTemplate restTemplate;

    @Resource
    OrderDao orderDao;

    public Order getOrder(Integer id) {
        return orderDao.selectOrderById(id);
    }

    public Integer createOrder(Order order) {
        int payMoney = order.getNum() * order.getPrice().intValue() * 100;
        JSONObject res = restTemplate.getForObject(PAYMENT_URL + payMoney, JSONObject.class);
        Integer code = (Integer) res.get("code");
        if (code == 200) return orderDao.insertOrder(order);

        return 0;
    }
}
