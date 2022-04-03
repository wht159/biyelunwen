package com.haitao.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Teacher;
import com.haitao.springboot.entity.User;
import com.haitao.springboot.mapper.TeacherMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import com.haitao.springboot.service.IStudentService;
import com.haitao.springboot.entity.Student;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private IStudentService studentService;


    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        studentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        studentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(studentService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(studentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String sno) {

        return Result.success(studentService.getStuPage(new Page<>(pageNum, pageSize),name,sno));
    }

    @GetMapping("/getStudentByTeaNum")
    public Result getStudentByTeaNum(@RequestParam String teaNum) {
        return Result.success(studentService.getStudentByTeaNum(teaNum));
    }



}

