package com.libing.libingdemo.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class DelCacheRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        try {
            log.info("开始执行项目启动后执行方法");

            log.info("完成执行项目启动后执行方法");
        } catch (Exception e) {
            log.error("删除缓存信息出错:{}", e.getMessage());
        }
    }
}
