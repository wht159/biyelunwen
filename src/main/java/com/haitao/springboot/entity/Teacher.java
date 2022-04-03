package com.haitao.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
 * @since 2022-03-27
 */
@Getter
@Setter
  @TableName("sys_teacher")
@ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      @ApiModelProperty("导师id")
        private Integer id;

      @ApiModelProperty("工号")
      private String tno;

      @ApiModelProperty("姓名")
      private String name;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("所在院系")
      private String dept;

      @ApiModelProperty("职称")
      private String title;

      @ApiModelProperty("联系电话")
      private String phone;

      @ApiModelProperty("邮箱")
      private String email;

      @TableField(exist = false)
      private List<Student> students;

}
