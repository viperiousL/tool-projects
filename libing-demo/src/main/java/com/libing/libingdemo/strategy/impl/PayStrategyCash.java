package com.libing.libingdemo.strategy.impl;

import com.libing.libingdemo.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("PayStrategyCash")
@Slf4j
public class PayStrategyCash implements PayStrategy {
    @Override
    public void pay(Long money) {
        System.out.println("现金支付了："+money);
    }
}
