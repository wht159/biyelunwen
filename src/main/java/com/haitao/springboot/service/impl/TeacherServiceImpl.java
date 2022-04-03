package com.haitao.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.StuProposal;
import com.haitao.springboot.entity.Student;
import com.haitao.springboot.entity.Teacher;
import com.haitao.springboot.mapper.StudentMapper;
import com.haitao.springboot.mapper.TeacherMapper;
import com.haitao.springboot.service.ITeacherService;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Resource
    private TeacherMapper teacherMapper;


    @Override
    public Page<Teacher> getTeaPage(Page<Teacher> page, String name, String tno) {
        return teacherMapper.getTeaPage(page, name, tno);
    }

    @Override
    public List<StuProposal> GetStuFileInfo(String teaNum,String fileType) {
        if (fileType.equals("proposal")){
            return teacherMapper.GetStuProposalInfo(teaNum);
        }else {
         return teacherMapper.GetStuPaperInfo(teaNum);
        }
    }
}
