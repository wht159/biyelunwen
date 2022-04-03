package com.haitao.springboot.mapper;

import com.haitao.springboot.entity.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴海韬
 * @since 2022-04-01
 */
public interface PaperMapper extends BaseMapper<Paper> {

    List<Boolean> getVrfStateByStuNum(@Param("stuNum") String stuNum);
}
