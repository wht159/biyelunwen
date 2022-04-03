package com.haitao.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Paper;
import com.haitao.springboot.mapper.ProposalMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.haitao.springboot.service.IProposalService;
import com.haitao.springboot.entity.Proposal;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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
@RequestMapping("/proposal")
public class ProposalController {

    @Value("${files.upload.path}")
    private String fileUploadPath;


    @Resource
    private IProposalService proposalService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Proposal proposal) {
        proposalService.saveOrUpdate(proposal);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        proposalService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        proposalService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(proposalService.list());
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Proposal> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(proposalService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, @RequestParam String stuNum) throws IOException {

        System.out.println("文件"+file);
        System.out.println("学号"+stuNum);
        return  proposalService.uploadProposal(file,stuNum);
    }

    //接收学生信息
    @GetMapping("/showProposalInfo")
    public Result showProposalInfo(@RequestParam String stuNum) {

        return Result.success(proposalService.showProposalInfo(stuNum));
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

}

