package com.libing.libingdemo.mapperTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libing.libingdemo.entity.Student;
import com.libing.libingdemo.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * com.libng.libingdemo.mapperTest
 *
 * @author hehongfei
 * @date 2022/6/1 15:41
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class pageTest {
    @Resource
    private StudentMapper studentMapper;

    @Test
    public void testPage() {
        //第一个参数：当前页     第二个参数：页面条数
        Page<Student> studentPage = new Page<>(1, 4);
        //设置条件
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender", "女");
        //查询
        studentMapper.selectPage(studentPage, queryWrapper);
        System.out.println("总页数： " + studentPage.getPages());
        System.out.println("总记录数： " + studentPage.getTotal());


        studentPage.getRecords().forEach(System.out::println);


    }
}
