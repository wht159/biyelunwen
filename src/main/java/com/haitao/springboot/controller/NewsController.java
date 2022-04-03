package com.haitao.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import com.haitao.springboot.service.INewsService;
import com.haitao.springboot.entity.News;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private INewsService newsService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody News news) {
        newsService.saveOrUpdate(news);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        newsService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        newsService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(newsService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(newsService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String title,
                           @RequestParam(defaultValue = "") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        IPage<News> page = new Page<>(pageNum,pageSize);
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        if(!title.equals("") && title != null){ queryWrapper.eq("title", title); }
        if(date != null){ queryWrapper.eq("create_time", date);}
        return Result.success(newsService.page(page, queryWrapper));
    }

}

