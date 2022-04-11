package com.haitao.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import com.haitao.springboot.service.ITeacherService;
import com.haitao.springboot.entity.Teacher;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private ITeacherService teacherService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Teacher teacher) {
        teacherService.saveOrUpdate(teacher);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        teacherService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        teacherService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(teacherService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(teacherService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String tno) {

        return Result.success(teacherService.getTeaPage(new Page<>(pageNum, pageSize), name,tno));
    }

    @GetMapping("/GetStuFileInfo")
    public Result GetStuFileInfo(@RequestParam String teaNum, @RequestParam String fileType) {

        return Result.success(teacherService.GetStuFileInfo(teaNum,fileType));
    }

    @GetMapping("/checkPaper")
    public Result checkPaper(@RequestParam String simHash, @RequestParam String stuNum){
        return  Result.success(teacherService.checkPaper(simHash,stuNum));
    }
}

