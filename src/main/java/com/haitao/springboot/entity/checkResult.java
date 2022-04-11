package com.haitao.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class checkResult {
    private double similarity;
    private String name;
    private String simHash;
    private String url;
    private Date createTime;
}
