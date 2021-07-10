package com.wuyuefeng.service;

import com.wuyuefeng.dao.PayDao;
import com.wuyuefeng.model.PayAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayService {

    @Resource
    PayDao payDao;

    public PayAccount getAccountInfo(Integer id) {
        return payDao.selectAccountById(id);
    }
}
