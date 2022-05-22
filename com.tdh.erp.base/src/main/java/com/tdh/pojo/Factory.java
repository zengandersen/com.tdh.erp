package com.tdh.pojo;

import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Factory extends QueryBean {

    private String factoryId;

    private String factoryName;

    private String factoryCode;

    private String pinyin;

    private String Ename;

    private String factoryTel;

    private String factoryAddr;

    private String factoryUrl;

    private String remark;

    private int orderNum;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}
