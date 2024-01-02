package com.libing.libingdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libing.libingdemo.entity.Student;
import com.libing.libingdemo.param.StudentParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author com.libng
 * @since 2022-06-01
 */
@Service
public interface IStudentService extends IService<Student> {

    public List<Student> getByParam(StudentParam param);

}
