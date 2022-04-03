package com.haitao.springboot.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haitao.springboot.common.Constants;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Proposal;
import com.haitao.springboot.entity.Title;
import com.haitao.springboot.mapper.ProposalMapper;
import com.haitao.springboot.mapper.TitleMapper;
import com.haitao.springboot.service.IProposalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-30
 */
@Service
public class ProposalServiceImpl extends ServiceImpl<ProposalMapper, Proposal> implements IProposalService {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Resource
    ProposalMapper proposalMapper;

    @Resource
    TitleMapper titleMapper;

    @Override
    public Result uploadProposal(MultipartFile file,String stuNum) throws IOException {

        QueryWrapper<Title> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_num", stuNum);
        Title title = titleMapper.selectOne(queryWrapper);
        if(title != null){
            if(title.getIsVerify() == true){
                String originalFilename = file.getOriginalFilename();
                String type = FileUtil.extName(originalFilename);
                long size = file.getSize();

                // 定义一个文件唯一的标识码
                String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;
                File uploadFile = new File(fileUploadPath + fileUUID);
                // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
                File parentFile = uploadFile.getParentFile();
                if(!parentFile.exists()) {
                    parentFile.mkdirs();
                }

                String url;
                // 获取文件的md5
                String md5 = SecureUtil.md5(file.getInputStream());
                // 从数据库查询是否存在相同的记录
                Proposal dbProposal = getFileByMd5(md5);
                if (dbProposal != null) {
                    url = dbProposal.getUrl();
                } else {
                    // 上传文件到磁盘
                    file.transferTo(uploadFile);
                    // 数据库若不存在重复文件，则不删除刚才上传的文件
                    url = "http://" + serverIp + ":9090/proposal/" + fileUUID;
                }

                // 存储数据库
                Proposal saveProposal = new Proposal();
                saveProposal.setName(originalFilename);
                saveProposal.setSize(size/1024); // 单位 kb
                saveProposal.setUrl(url);
                saveProposal.setMd5(md5);
                saveProposal.setStuNum(stuNum);
                proposalMapper.insert(saveProposal);
                return Result.success(url);
            }else return Result.error(Constants.CODE_400,"课题审核未通过");
        }else return Result.error(Constants.CODE_400,"请先选择课题");




    }

    @Override
    public Proposal showProposalInfo(String stuNum) {
        //查找开题报告信息
        QueryWrapper<Proposal> QWproposal = new QueryWrapper<>();
        QWproposal.eq("stu_num", stuNum);
        QWproposal.orderByDesc("create_time");
        if (proposalMapper.selectList(QWproposal).size() != 0) {
            Proposal proposal =  proposalMapper.selectList(QWproposal).get(0);
            //查找课题信息
            QueryWrapper<Title> QWtitle = new QueryWrapper<>();
            QWtitle.eq("stu_num", stuNum);
            String titleName = titleMapper.selectOne(QWtitle).getName();

            if(proposal != null){
                proposal.setTitleName(titleName);
            }
            return proposal;
        }
        return null;
    }

    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    private Proposal getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Proposal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Proposal> proposalList = proposalMapper.selectList(queryWrapper);
        return proposalList.size() == 0 ? null : proposalList.get(0);
    }
}
