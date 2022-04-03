package com.haitao.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.StuProposal;
import com.haitao.springboot.entity.Student;
import com.haitao.springboot.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-27
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    Page<Teacher> getTeaPage(Page<Teacher> page, @Param("name") String name, @Param("tno")String tno);

    List<StuProposal> GetStuProposalInfo(@Param("teaNum") String teaNum);

    List<StuProposal> GetStuPaperInfo(@Param("teaNum") String teaNum);
}
