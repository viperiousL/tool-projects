package com.libing.libingdemo.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class StudentParam {

    private Integer id;

    private Integer stuNum;

    private String stuName;

    private Integer age;

    @Length(message = "性别不能超过{max}字符", max = 1)
    private String gender;

    private LocalDate birthday;

}
