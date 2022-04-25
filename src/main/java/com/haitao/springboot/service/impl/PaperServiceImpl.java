package com.haitao.springboot.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haitao.springboot.common.Constants;
import com.haitao.springboot.common.Result;
import com.haitao.springboot.entity.Paper;
import com.haitao.springboot.entity.Proposal;
import com.haitao.springboot.entity.Title;
import com.haitao.springboot.mapper.PaperMapper;
import com.haitao.springboot.mapper.TitleMapper;
import com.haitao.springboot.service.IPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haitao.springboot.utils.SimHashUtils;
import com.haitao.springboot.utils.WordUtils;
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
 * @since 2022-04-01
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {


    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Resource
    private PaperMapper paperMapper;


    @Resource
    TitleMapper titleMapper;


    @Override
    public Paper showPaperInfo(String stuNum) {
        //查找论文信息

        QueryWrapper<Paper> QWPaper = new QueryWrapper<>();
        QWPaper.eq("stu_num", stuNum);
        QWPaper.orderByDesc("create_time");
        if (paperMapper.selectList(QWPaper).size() != 0){
            Paper paper = paperMapper.selectList(QWPaper).get(0);
            //查找论文信息
            QueryWrapper<Title> QWtitle = new QueryWrapper<>();
            QWtitle.eq("stu_num", stuNum);
            String titleName = titleMapper.selectOne(QWtitle).getName();
            if(paper != null){
                paper.setTitleName(titleName);
            }
            return paper;
        }
        return null;
    }

    @Override
    public Result uploadPaper(MultipartFile file, String stuNum) throws IOException {

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
                String fileTotalPath  = fileUploadPath + fileUUID;
                File uploadFile = new File(fileTotalPath);
                // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
                File parentFile = uploadFile.getParentFile();
                if(!parentFile.exists()) {
                    parentFile.mkdirs();
                }

                String url;
                // 获取文件的md5
                String md5 = SecureUtil.md5(file.getInputStream());
                // 从数据库查询是否存在相同的记录
                Paper dbPaper = getFileByMd5(md5);
                if (dbPaper != null) {
                    url = dbPaper.getUrl();
                } else {
                    // 上传文件到磁盘
                    file.transferTo(uploadFile);
                    // 数据库若不存在重复文件，则不删除刚才上传的文件
                    url = "http://" + serverIp + ":9090/proposal/" + fileUUID;
                }

                String simHash = getSimHash(fileTotalPath);

                // 存储数据库
                Paper savePaper = new Paper();
                savePaper.setName(originalFilename);
                savePaper.setSize(size/1024); // 单位 kb
                savePaper.setUrl(url);
                savePaper.setMd5(md5);
                savePaper.setStuNum(stuNum);
                savePaper.setSimHash(simHash);
                paperMapper.insert(savePaper);
                return Result.success(url);
            }else return Result.error(Constants.CODE_400,"课题审核未通过");
        }else return Result.error(Constants.CODE_400,"请先选择课题");
    }

    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    private Paper getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Paper> paperList = paperMapper.selectList(queryWrapper);
        return paperList.size() == 0 ? null : paperList.get(0);
    }

    /**
     * 获取SimHash值
     * @param uploadFile
     * @return
     * @throws IOException
     */
    private String getSimHash(String uploadFile) throws IOException {
        String result= WordUtils.ReadDoc(uploadFile);
        return SimHashUtils.getSimHash(result);
    }


}
