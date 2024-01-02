package com.libing.libingdemo.service.impl;

import com.libing.libingdemo.annotation.UserAnno;
import com.libing.libingdemo.entity.Student;
import com.libing.libingdemo.event.StudentEvent;
import com.libing.libingdemo.service.StudentRegisterService;
import com.libing.libingdemo.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;

@Service
public class StudentRegisterServiceImpl implements StudentRegisterService {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private Map<String, PayStrategy> payStrategyMap;

    @Override
    @UserAnno("aaaa")
    public void register() {
        System.out.println(payStrategyMap);
        Student student = new Student();
        student.setId(1);
        student.setStuNum(9527);
        student.setStuName("tom");
        student.setAge(18);
        student.setGender("男");
        student.setBirthday(LocalDate.now());
        StudentEvent studentEvent = new StudentEvent(this, student);
        applicationEventPublisher.publishEvent(studentEvent);
        System.out.println("结束了");
    }
}
