package com.haitao.springboot.service;

import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Paper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-04-01
 */
public interface IPaperService extends IService<Paper> {

    Paper showPaperInfo(String stuNum);

    Result uploadPaper(MultipartFile file, String stuNum) throws IOException;
}
