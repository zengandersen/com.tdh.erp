package com.tdh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Repertory extends QueryBean {

    @JsonProperty("repertory_id")
    private String repertoryId;

    @JsonProperty("goods_id")
    private String goodsId;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("total")
    private int total;

    @JsonProperty("warning_num")
    private int warningNum;

    @JsonProperty("remark")
    private int remark;

    @JsonProperty("create_user")
    private String createUser;

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("update_user")
    private String updateUser;

    @JsonProperty("update_time")
    private String updateTime;


}
