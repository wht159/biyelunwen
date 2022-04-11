package com.haitao.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.StuFile;


import com.haitao.springboot.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haitao.springboot.entity.checkResult;

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

    List<StuFile> GetStuFileInfo(String teaNum, String fileType);

    List<checkResult> checkPaper(String simHash, String stuNum);
}
