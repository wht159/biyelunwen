package com.haitao.springboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface StuTeaMapper {
    @Select("select tea_num from sys_student_teacher where stu_num = #{stu_num}")
    Integer getTeaNum(@Param("stu_num") Integer stu_num);

    @Update("update sys_student_teacher set tea_num = #{tea_num}  where stu_num = #{stu_num}")
    Integer update(@Param("stu_num") Integer stu_num, @Param("tea_num") Integer tea_num);

    @Insert("insert into sys_student_teacher (stu_num, tea_num) values (#{stu_num}, #{tea_num})")
    Integer addStuTea(@Param("stu_num") Integer stu_num, @Param("tea_num") Integer tea_num);
}
