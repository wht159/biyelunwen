package com.haitao.springboot.service;

import com.haitao.springboot.entity.Verify;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-04-01
 */
public interface IVerifyService extends IService<Verify> {

    Verify gerByFileId(Integer file_id);
}
