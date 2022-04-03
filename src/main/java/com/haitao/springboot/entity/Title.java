package com.haitao.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴海韬
 * @since 2022-03-28
 */
@Getter
@Setter
@ToString
  @TableName("sys_title")
@ApiModel(value = "Title对象", description = "")
public class Title implements Serializable {



      @TableId(value = "id", type = IdType.AUTO)
      @ApiModelProperty("课题id")
      private Integer id;

      @ApiModelProperty("课题名称")
      private String name;

      @ApiModelProperty("课题难度")
      private String difficulty;

      @ApiModelProperty("课题类型")
      private String type;

      @ApiModelProperty("课题来源")
      private String source;

      @ApiModelProperty("课题简介")
      private String brief;

      @ApiModelProperty("课题指导老师工号")
      private String teaNum;

      @ApiModelProperty("课题学生学号")
      private String stuNum;

      @ApiModelProperty("是否审核通过")
      private Boolean isVerify;

      @ApiModelProperty("最终成绩")
      private Float score;

      @TableField(exist = false)
      private String tea_name;

      @TableField(exist = false)
      private String stu_name;

      @TableField(exist = false)
      private Integer flag;


}
