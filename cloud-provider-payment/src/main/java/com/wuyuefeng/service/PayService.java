package com.wuyuefeng.service;

import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.config.MoneyConstant;
import com.wuyuefeng.dao.PayDao;
import com.wuyuefeng.model.PayAccount;
import com.wuyuefeng.model.ResponseMsg;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class PayService {

    @Resource
    PayDao payDao;

    public PayAccount getAccountInfo(Integer id) {
        return payDao.selectAccountById(id);
    }

    /**
     * 支付
     *
     * @param accountId 账户id
     * @param money     扣款金额，入参是long，按照放大十倍处理
     * @return
     */
    public JSONObject pay(Integer accountId, Long money) {
        PayAccount account = payDao.selectAccountById(accountId);
        BigDecimal currentAccountMoney = account.getAccountMoney();
        // 余额不足，支付失败
        BigDecimal shouldPay = new BigDecimal(money / MoneyConstant.MONEY_ENLARGE);
        if (currentAccountMoney.subtract(shouldPay).compareTo(new BigDecimal(0)) < 0)
            return ResponseMsg.fail("余额不足");
        // 账户剩余金额
        BigDecimal accountMoney = currentAccountMoney.subtract(shouldPay);
        Integer num = payDao.descAccountMoney(accountId, accountMoney);
        if (num == 1) return ResponseMsg.success("支付成功");
        return ResponseMsg.fail("支付失败");
    }
}
