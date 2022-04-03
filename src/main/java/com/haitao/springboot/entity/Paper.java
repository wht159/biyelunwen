package com.haitao.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴海韬
 * @since 2022-04-01
 */
@Getter
@Setter
  @TableName("sys_paper")
@ApiModel(value = "Paper对象", description = "")
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("文件名称")
      private String name;

      @ApiModelProperty("论文大小")
      private Long size;

      @ApiModelProperty("下载链接")
      private String url;

      @ApiModelProperty("文件MD5")
      private String md5;

      @ApiModelProperty("学生学号")
      private String stuNum;

      @ApiModelProperty("上传时间")
      private LocalDateTime createTime;

      @TableField(exist = false)
      private String titleName;

}
