package com.libing.libingdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.libng.unittesting.controller
 *
 * @author hehongfei
 * @date 2022/5/30 14:47
 */

/**
 * 用来测试springboot的web依赖是否正常
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/mybatis")
    public String hello() {
        return "test mybatis-plus";
    }

}
