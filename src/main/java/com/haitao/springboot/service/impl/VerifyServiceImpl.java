package com.haitao.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haitao.springboot.entity.Teacher;
import com.haitao.springboot.entity.Verify;
import com.haitao.springboot.mapper.TeacherMapper;
import com.haitao.springboot.mapper.VerifyMapper;
import com.haitao.springboot.service.IVerifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-04-01
 */
@Service
public class VerifyServiceImpl extends ServiceImpl<VerifyMapper, Verify> implements IVerifyService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Verify gerByFileId(Integer file_id) {
        //查找到带审核老师num的记录
        Verify verify = getOne(new QueryWrapper<Verify>().eq("file_id", file_id));
        //通过tea_num查找审查老师名称
        if (verify != null) {
            Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("tno",verify.getTeaNum()));
            verify.setVerifyName(teacher.getName());
            return verify;
        }
       return null;
    }
}
