package com.wuyuefeng.dao;

import com.wuyuefeng.model.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderDao {
    Order selectOrderById(@Param("id") Integer id);

    Integer insertOrder(Order order);
}
