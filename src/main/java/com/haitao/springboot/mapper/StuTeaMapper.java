package com.haitao.springboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface StuTeaMapper {
    @Select("select tea_id from sys_student_teacher where stu_id = #{stu_id}")
    Integer getTeaId(@Param("stu_id") Integer stu_id);

    @Update("update sys_student_teacher set tea_id = #{tea_id}  where stu_id = #{stu_id}")
    Integer update(@Param("stu_id") Integer stu_id, @Param("tea_id") Integer tea_id);

    @Insert("insert into sys_student_teacher (stu_id, tea_id) values (#{stu_id}, #{tea_id})")
    Integer addStuTea(@Param("stu_id") Integer stu_id, @Param("tea_id") Integer tea_id);
}
