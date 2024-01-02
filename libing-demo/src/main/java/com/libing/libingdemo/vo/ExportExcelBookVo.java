package com.libing.libingdemo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data   // Lombok注解，用于生成getter setter
@Accessors(chain = true) //Lombok注解，链式赋值使用
public class ExportExcelBookVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ColumnWidth(25)
    @ExcelProperty(value = {"用户名称"}, index = 0)
    private String name;

    @ColumnWidth(25)
    @ExcelProperty(value = {"用户年龄"}, index = 1)
    private Integer readerNum;

    @ColumnWidth(25)
    @ExcelProperty(value = {"用户编号"}, index = 2)
    private Integer price;

}