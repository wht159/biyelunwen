package com.haitao.springboot.service;

import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Proposal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-30
 */
public interface IProposalService extends IService<Proposal> {

    Result uploadProposal(MultipartFile file, String stuNum) throws IOException;

    Proposal showProposalInfo(String stuNum);
}
