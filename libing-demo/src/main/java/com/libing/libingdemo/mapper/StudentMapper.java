package com.libing.libingdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libing.libingdemo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author com.libng
 * @since 2022-06-01
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
