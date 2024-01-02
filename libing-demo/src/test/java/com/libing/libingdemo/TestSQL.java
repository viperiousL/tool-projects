package com.libing.libingdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * com.libng.springbootswagger
 *
 * @author hehongfei
 * @date 2022/5/25 15:50
 */
@SpringBootTest
public class TestSQL {
    @Resource
    private DataSource dataSource;


    @Test
    void getConnetion() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
