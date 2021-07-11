package com.wuyuefeng.dao;

import com.wuyuefeng.model.PayAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface PayDao {
    PayAccount selectAccountById(@Param("id") Integer id);

    Integer descAccountMoney(@Param("accountId") Integer accountId, @Param("accountMoney") BigDecimal money);
}
