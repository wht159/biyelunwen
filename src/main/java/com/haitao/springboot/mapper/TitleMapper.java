package com.haitao.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.entity.Title;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-28
 */
public interface TitleMapper extends BaseMapper<Title> {

    Page<Title> pageTitle(Page<Object> page);

    List <Title> getResult(@Param("stuNum") String stuNum, @Param("teaNum") String teaNum);

    @Update("update sys_title set stu_num = '' where id = #{id}")
    void WithdrawalTitle(@Param("id") Integer id);
}


