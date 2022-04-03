package com.haitao.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import com.haitao.springboot.service.IPaperService;
import com.haitao.springboot.entity.Paper;

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
 * @since 2022-04-01
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Resource
    private IPaperService paperService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Paper paper) {
        paperService.saveOrUpdate(paper);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        paperService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        paperService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(paperService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(paperService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(paperService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/showPaperInfo")
    public Result showPaperInfo(@RequestParam String stuNum) {
        return Result.success(paperService.showPaperInfo(stuNum));
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, @RequestParam String stuNum) throws IOException {

        System.out.println("文件"+file);
        System.out.println("学号"+stuNum);
        return  paperService.uploadProposal(file,stuNum);
    }

}

