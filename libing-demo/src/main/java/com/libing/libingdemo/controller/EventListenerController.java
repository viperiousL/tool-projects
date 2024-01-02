package com.libing.libingdemo.controller;

import com.libing.libingdemo.service.StudentRegisterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/event")
public class EventListenerController {
    @Resource
    private StudentRegisterService studentEventRegisterService;

    @GetMapping("/registerUser")
    public void register() {
        try {
            studentEventRegisterService.register();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
