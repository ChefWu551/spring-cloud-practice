package com.wuyuefeng.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayAccount {

    private Integer id;

    private String accountName;

    private BigDecimal accountMoney;

}
