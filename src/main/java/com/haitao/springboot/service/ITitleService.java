package com.haitao.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Title;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-28
 */
public interface ITitleService extends IService<Title> {

    Page<Title> pageTitle(Page<Object> objectPage);

    Result selectTitle(Integer id, String stuNum, Integer flag);

    List<Title> getResult(String stuNum, String teaNum);

    void Withdrawal(Integer id, String stuNum, Integer flag);
}
