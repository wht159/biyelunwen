package com.haitao.springboot.service.impl;

import com.haitao.springboot.entity.News;
import com.haitao.springboot.mapper.NewsMapper;
import com.haitao.springboot.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-30
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
