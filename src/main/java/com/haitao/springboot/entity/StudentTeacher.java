package com.haitao.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_student_teacher")
@Data
public class StudentTeacher {

    private Integer stu_id;
    private Integer tea_id;

}