package com.haitao.springboot.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haitao.springboot.entity.Files;



public interface IFileService extends IService<Files> {

    Page<Files> findPageFile(Page<Files> objectPage, Integer flag, String name);

}
