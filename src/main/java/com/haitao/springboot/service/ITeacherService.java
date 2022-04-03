package com.haitao.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.StuProposal;
import com.haitao.springboot.entity.Student;
import com.haitao.springboot.entity.Teacher;
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
public interface ITeacherService extends IService<Teacher> {

    Page<Teacher> getTeaPage(Page<Teacher> page, String name, String tno);

    List<StuProposal> GetStuFileInfo(String teaNum,String fileType);
}
