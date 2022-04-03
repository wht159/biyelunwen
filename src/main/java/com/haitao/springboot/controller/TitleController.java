package com.haitao.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Constants;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Files;
import com.haitao.springboot.entity.Paper;
import com.haitao.springboot.entity.User;
import com.haitao.springboot.mapper.PaperMapper;
import com.haitao.springboot.mapper.StudentMapper;
import com.haitao.springboot.mapper.TitleMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import com.haitao.springboot.service.ITitleService;
import com.haitao.springboot.entity.Title;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-28
 */
@RestController
@RequestMapping("/title")
public class TitleController {

    @Resource
    private ITitleService titleService;

    @Resource
    private PaperMapper paperMapper;


    @Resource
    private TitleMapper titleMapper;

    @Resource
    private StudentMapper studentMapper;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Title title) {
        titleService.saveOrUpdate(title);
        return Result.success();
    }

    // 修改成绩
    @PostMapping("/editScore")
    public Result editScore(@RequestBody Title title) {
        System.out.println(title);
        QueryWrapper<Title> qwTitle = new QueryWrapper();
        qwTitle.eq("stu_num",title.getStuNum());
        titleService.update(title,qwTitle);
        return Result.success();
    }

    // 添加成绩
    @PostMapping("/addScore")
    public Result addScore(@RequestBody Title title) {
        List<Boolean> vrfStateByStuNum = paperMapper.getVrfStateByStuNum(title.getStuNum());
        if(vrfStateByStuNum.size() != 0){
            if(vrfStateByStuNum.get(0) != null){
                if(vrfStateByStuNum.get(0) == true){
                    QueryWrapper<Title> qwTitle = new QueryWrapper();
                    qwTitle.eq("stu_num",title.getStuNum());
                    Title one = titleService.getOne(qwTitle);
                    if (one.getScore() == null) {
                        titleService.update(title,qwTitle);
                        return Result.success();
                    }else  return Result.error(Constants.CODE_400,"该学生已有成绩");
                }
            }
        }
        return Result.error(Constants.CODE_400,"该学生毕业论文未通过");
    }

    //选课
    @PostMapping("/selectTitle")
    public Result selectTitle(@RequestBody Title title) {
        if(getTitleByStuNum(title.getStuNum()) == null){
            titleMapper.updateById(title);
            studentMapper.setIsSelect(title.getStuNum(),title.getFlag());
            return Result.success();
        }else return Result.error(Constants.CODE_400,"选题失败！只能选择一个题目,如需改选,请先退选其它毕业题目!");

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        titleService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        titleService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(titleService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(titleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        return Result.success(titleService.pageTitle(new Page<>(pageNum, pageSize)));
    }

    @GetMapping("/getResult")
    public Result getResult(@RequestParam(defaultValue = "") String stuNum,@RequestParam(defaultValue = "") String teaNum) {
        return  Result.success(titleService.getResult(stuNum,teaNum));
    }


    //退选选题
    @PostMapping("/Withdrawal")
    public Result Withdrawal(@RequestBody Title title) {
        Integer flag = title.getFlag();
        Integer id = title.getId();
        String stuNum = title.getStuNum();
        titleService.Withdrawal(id,stuNum,flag);
        return Result.success();
    }

    //审核选题
    @PostMapping("/verify")
    public Result Verify(@RequestBody Title title) {
        return Result.success(titleService.updateById(title));
    }

    private Title getTitleByStuNum(String stu_num) {

        QueryWrapper<Title> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_num", stu_num);
        List<Title> TitleList = titleMapper.selectList(queryWrapper);
        return TitleList.size() == 0 ? null : TitleList.get(0);
    }


}

