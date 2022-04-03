package com.haitao.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haitao.springboot.entity.Files;
import com.haitao.springboot.entity.User;
import com.haitao.springboot.mapper.FileMapper;
import com.haitao.springboot.service.IFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class FilesServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {

    @Resource
    private FileMapper fileMapper;


    @Override
    public Page<Files> findPageFile(Page<Files> page, Integer flag, String name) {
        return fileMapper.findPageFile(page,flag,name);
    }

}
