package com.libing.libingdemo.param;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentParam {

    private Integer id;

    private Integer stuNum;

    private String stuName;

    private Integer age;

    private String gender;

    private LocalDate birthday;

}
