package com.haitao.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.Student;
import com.haitao.springboot.mapper.StudentMapper;
import com.haitao.springboot.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-27
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Page<Student> getStuPage(Page<Student> page, String name, String sno) {
        return studentMapper.getStuPage(page,name,sno);
    }

    @Override
    public List<Student> getStudentByTeaNum(String tea_num) {
        return studentMapper.getStudentByTeaNum(tea_num);
    }
}
