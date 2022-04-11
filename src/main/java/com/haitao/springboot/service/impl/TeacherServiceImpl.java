package com.haitao.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Constants;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.StuFile;
import com.haitao.springboot.entity.Teacher;
import com.haitao.springboot.entity.checkResult;
import com.haitao.springboot.mapper.TeacherMapper;
import com.haitao.springboot.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haitao.springboot.utils.HammingUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public List<StuFile> GetStuFileInfo(String teaNum, String fileType) {
        if (fileType.equals("proposal")){
            return teacherMapper.GetStuProposalInfo(teaNum);
        }else {
         return teacherMapper.GetStuPaperInfo(teaNum);
        }
    }

    @Override
    public List<checkResult> checkPaper(String simHash, String stuNum) {
        List<checkResult> simHashes  = teacherMapper.getSimHashes(stuNum);
        List<checkResult> checkPapers  = new ArrayList<>();
        for (checkResult DateSimHash : simHashes) {
            if (HammingUtils.getSimilarity(DateSimHash.getSimHash(), simHash) > 0.80) {
                double similarity = HammingUtils.getSimilarity(DateSimHash.getSimHash(), simHash);
                DateSimHash.setSimilarity(similarity);
                checkPapers.add(DateSimHash);
            }

        }
        if (checkPapers.size() > 0) return checkPapers;
        else return null;
    }






}
