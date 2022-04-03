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
  @TableName("sys_verify")
@ApiModel(value = "Verify对象", description = "")
public class Verify implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("审核表id")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("审核老师工号")
      private String teaNum;

      @ApiModelProperty("审核时间")
      private LocalDateTime time;

      @ApiModelProperty("审核状态")
      private Boolean state;

      @ApiModelProperty("审核意见")
      private String comment;

      @ApiModelProperty("文件id")
      private Integer fileId;

      @TableField(exist = false)
      private String VerifyName;


}
