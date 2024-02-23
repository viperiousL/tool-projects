package com.libing.libingdemo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static void main(String[] args) {
        try {
            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dft.parse("2023-11-26");
            Date end = new Date();
            Long startTime = start.getTime();
            Long endTime = end.getTime();
            Long num = endTime - startTime;
            Long days = num / 24 / 60 / 60 / 1000;
            System.out.println("相差" + days + "天");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
