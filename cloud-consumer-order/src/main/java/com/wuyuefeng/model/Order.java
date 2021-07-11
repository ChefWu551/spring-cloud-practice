package com.wuyuefeng.model;

import lombok.Data;

@Data
public class Order {

    private Integer id;

    private Integer productId;

    private String productName;

    private Integer num;

    private Double price;
}
