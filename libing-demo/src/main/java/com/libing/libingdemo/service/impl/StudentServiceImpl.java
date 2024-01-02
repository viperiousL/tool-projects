package com.libing.libingdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libing.libingdemo.entity.Student;
import com.libing.libingdemo.event.StudentEvent;
import com.libing.libingdemo.mapper.StudentMapper;
import com.libing.libingdemo.param.StudentParam;
import com.libing.libingdemo.service.IStudentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author com.libng
 * @since 2022-06-01
 */
@Service("IStudentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<Student> getByParam(StudentParam param) {
        List<Student> students;
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id", param.getId());
//        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Student::getId, param.getId());
        students = this.getBaseMapper().selectList(wrapper);
        System.out.println("students" + students);
        return students;
    }

    @Transactional
    public Integer inert(Student student) {
        this.getBaseMapper().insert(student);
        applicationEventPublisher.publishEvent(new StudentEvent(this, student));
        return student.getId();
    }
}
