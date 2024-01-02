package com.libing.libingdemo.event;

import com.libing.libingdemo.entity.Student;
import org.springframework.context.ApplicationEvent;

public class StudentEvent extends ApplicationEvent {

    private Student student;

    public StudentEvent(Object source, Student student) {
        super(source);
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
