package com.haitao.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.Files;
import com.haitao.springboot.entity.User;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends BaseMapper<Files> {

    Page<Files> findPageFile(Page<Files> page, @Param("flag") Integer flag, @Param("name") String name);

}
