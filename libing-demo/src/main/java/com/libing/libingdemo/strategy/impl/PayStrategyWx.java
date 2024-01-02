package com.libing.libingdemo.strategy.impl;

import com.libing.libingdemo.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("PayStrategyWx")
@Slf4j
public class PayStrategyWx implements PayStrategy {
    @Override
    public void pay(Long money) {
        System.out.println("微信支付了："+money);
    }
}
