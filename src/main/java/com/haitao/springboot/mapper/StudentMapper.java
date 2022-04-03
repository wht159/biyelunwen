package com.haitao.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-27
 */
public interface StudentMapper extends BaseMapper<Student> {

    Page<Student> getStuPage(Page<Student> page, @Param("name") String name, @Param("sno")String sno);

    @Select("select * from sys_student where sno = #{sno}}")
    Student getStuSno(@Param("sno") String sno);

    @Select("select is_select from sys_student where sno = #{stuNum}")
    Integer getIsSelect(@Param("stuNum") String stuNum);

    @Update("update sys_student set is_select = #{flag} where sno = #{stuNum}")
    void setIsSelect(@Param("stuNum") String stuNum, @Param("flag") Integer flag);

    List<Student> getStudentByTeaNum(@Param("tea_num")String tea_num);
}
