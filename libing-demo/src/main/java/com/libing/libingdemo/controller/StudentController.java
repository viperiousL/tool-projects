package com.libing.libingdemo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libing.libingdemo.entity.Student;
import com.libing.libingdemo.param.StudentParam;
import com.libing.libingdemo.service.IStudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author com.libng
 * @since 2022-06-01
 */

/**
 * localhost:8082/student/save
 * localhost:8082/student/deleteById
 * localhost:8082/student/updateById
 * localhost:8082/student/getById
 * localhost:8082/student/getOneById
 * localhost:8082/student/getOneByStuName
 * localhost:8082/student/getStudentListBySex
 * localhost:8082/student/listStudentListByAge
 * localhost:8082/student/testpage
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private IStudentService iStudentService;

    /**
     * 存储新用户
     */
    @GetMapping("/save")
    public void save() {
//		Student student = new Student(44, 232323, "小芳", 19, "女", LocalDate.now());
        Student student = new Student();
        student.setAge(44);
        student.setStuName("小芳1");
        student.setStuNum(19);
        student.setGender("女");
        student.setBirthday(LocalDate.now());
        boolean save = iStudentService.save(student);

    }

    /**
     * 根据id删除用户
     *
     * @param id
     */
    @GetMapping("/deleteById")
    public void deleteById(@PathParam("id") Integer id) {
        boolean b = iStudentService.removeById(id);
    }

    /**
     * 根据id修改用户
     */
    @GetMapping("/updateById")
    public void updateById() {
        Student student = new Student(44, 3453454, "小明", 19, "女", LocalDate.now());
        boolean b = iStudentService.updateById(student);
    }

    /**
     * 根据id搜索用户
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public String getById(@PathParam("id") Integer id) {
        Student student = iStudentService.getById(id);

        System.out.println(student);
        return student.toString();
    }

    /**
     * 根据param搜索用户
     *
     * @param param
     * @return
     */
    @PostMapping("/getByParam")
    public String getByParam(@RequestBody @Valid StudentParam param) {
        List<Student> student = iStudentService.getByParam(param);
        return student.toString();
    }

    /**
     * mybatis-plus测试使用queryWrapper，  根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/getOneById")
    public Student getOneById(@PathParam("id") Integer id) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Student student = iStudentService.getOne(queryWrapper);
        return student;
    }

    /**
     * 根据学生名查询学生信息
     *
     * @param stuName
     * @return
     */
    @GetMapping("/getOneByStuName")
    public Student getOneByStuName(@PathParam("stuName") String stuName) {
        System.out.println("------------------------------------------");
//        设置查询条件
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_name", stuName);

        Student student = iStudentService.getOne(queryWrapper);
        System.out.println("-----------------------------------------");
        System.out.println(student.toString());
        return student;
    }

    /**
     * 按照性别查询所有符合要求的所有用户
     *
     * @return
     */


    @GetMapping("/getStudentListBySex")
    public List<Student> list(@PathParam("gender") String gender) {
//
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender", gender);

        List<Student> list = iStudentService.list(queryWrapper);
        return list;
    }

    /**
     * 按照年龄查询所有符合要求的所有用户
     *
     * @return
     */
    @GetMapping("/listStudentListByAge")
    public List<Student> listStudentListByAge(@PathParam("age") Integer age) {
//
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("age", age);

        List<Student> list = iStudentService.list(queryWrapper);
        return list;
    }

    /**
     * 测试分页查询
     */
    @GetMapping("/testPage")
    public JSON testPage() {
        //第一个参数：当前页     第二个参数：页面大小
        Page<Student> studentPage = new Page<>(1, 4);
        //设置条件
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender", "女");


        iStudentService.page(studentPage, queryWrapper);
        //查询

        //控制台输出
        System.out.println("总页数： " + studentPage.getPages());
        System.out.println("总记录数： " + studentPage.getTotal());
        studentPage.getRecords().forEach(System.out::println);
        //返回前端
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", studentPage.getTotal());
        jsonObject.put("rows", studentPage.getRecords());
        jsonObject.put("page", studentPage.getCurrent());
        return jsonObject;

    }


}
