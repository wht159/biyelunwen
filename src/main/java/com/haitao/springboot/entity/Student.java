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
@TableName("sys_student")
@ApiModel(value = "Student对象", description = "")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("学生id")
    private Integer id;

    @ApiModelProperty("学号")
    private String sno;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("所在院系")
    private String dept;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("是否选课")
    private String isSelect;

    @ApiModelProperty("导师名字")
    @TableField(exist = false)
    private String tea_name;

    @TableField(exist = false)
    @ApiModelProperty("课题名称")
    private String titleName;

    @TableField(exist = false)
    @ApiModelProperty("课题类型")
    private String type;

    @TableField(exist = false)
    @ApiModelProperty("课题难度")
    private String difficulty;

    @TableField(exist = false)
    @ApiModelProperty("课题难度")
    private String score;

}
