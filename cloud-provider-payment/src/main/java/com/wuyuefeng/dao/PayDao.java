package com.wuyuefeng.dao;

import com.wuyuefeng.model.PayAccount;
import org.apache.ibatis.annotations.Param;

public interface PayDao {
    PayAccount selectAccountById(@Param("id") Integer id);
}
