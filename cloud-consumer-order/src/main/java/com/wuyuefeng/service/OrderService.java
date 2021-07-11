package com.wuyuefeng.service;

import com.wuyuefeng.dao.OrderDao;
import com.wuyuefeng.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    OrderDao orderDao;

    public Order getOrder(Integer id) {
        return orderDao.selectOrderById(id);
    }

    public Integer createOrder(Order order) {
        return orderDao.insertOrder(order);
    }
}
