package com.haitao.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class StuFile {
    private String sno;
    private String stuName;
    private Integer id;
    private Date createTime;
    private Boolean state;
    private String url;
    private String simHash;
}
