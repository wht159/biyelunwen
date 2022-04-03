package com.haitao.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import com.haitao.springboot.service.IVerifyService;
import com.haitao.springboot.entity.Verify;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/verify")
public class VerifyController {

    @Resource
    private IVerifyService verifyService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Verify verify) {
        verifyService.saveOrUpdate(verify);
        return Result.success();
    }


    @GetMapping
    public Result findAll() {
        return Result.success(verifyService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(verifyService.getById(id));
    }

    @GetMapping("/gerByFileId")
    public Result gerByFileId(@RequestParam Integer file_id) {
        return Result.success(verifyService.gerByFileId(file_id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Verify> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(verifyService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

