package com.haitao.springboot.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitao.springboot.common.Constants;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Title;
import com.haitao.springboot.entity.User;
import com.haitao.springboot.exception.ServiceException;
import com.haitao.springboot.mapper.StudentMapper;
import com.haitao.springboot.mapper.TitleMapper;
import com.haitao.springboot.service.ITitleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-28
 */
@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements ITitleService {

    private static final Log LOG = Log.get();

    @Resource
    private TitleMapper titleMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Page<Title> pageTitle(Page<Object> Page) {
        return titleMapper.pageTitle(Page);
    }

    @Override
    public Result selectTitle(Integer id, String stuNum,Integer flag) {

        if(studentMapper.getIsSelect(stuNum) == 0){
            Title title = new Title();
            title.setId(id);
            title.setStuNum(stuNum);
            updateById(title);
            studentMapper.setIsSelect(stuNum,flag);
            return Result.success();
        }else {
            return Result.error(Constants.CODE_400,"选题失败！只能选择一个题目,如需改选,请先退选其它毕业题目!");
        }
    }

    @Override
    public List<Title> getResult(String stuNum, String teaNum) {
        return titleMapper.getResult(stuNum,teaNum);
    }

    @Override
    public void Withdrawal(Integer id, String stuNum, Integer flag) {
        studentMapper.setIsSelect(stuNum,flag);
        titleMapper.WithdrawalTitle(id);
    }

}
