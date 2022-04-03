package com.haitao.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-27
 */
public interface IStudentService extends IService<Student> {

    Page<Student> getStuPage(Page<Student> page, String name, String sno);

    List<Student> getStudentByTeaNum(String tea_num);
}
