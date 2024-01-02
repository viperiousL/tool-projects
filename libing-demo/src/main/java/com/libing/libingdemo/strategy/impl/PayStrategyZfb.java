package com.libing.libingdemo.strategy.impl;

import com.libing.libingdemo.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("PayStrategyZfb")
@Slf4j
public class PayStrategyZfb implements PayStrategy {

    @Override
    public void pay(Long money) {
        System.out.println("支付宝支付了："+money);
    }
}
