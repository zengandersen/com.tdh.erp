package com.tdh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class MealBindRes extends QueryBean{

    @JsonProperty("factory_name")
    private String factory_name;
    @JsonProperty ("factory_id")
    private String factory_id;
    @JsonProperty("repertory_id")
    private String repertory_id;
    @JsonProperty("goods_id")
    private String goods_id;
    @JsonProperty("goods_name")
    private String goods_name;
    @JsonProperty("goods_img")
    private String goods_img;
    @JsonProperty("goods_code")
    private String goods_code;
    @JsonProperty("total")
    private int total;
    @JsonProperty("warning_num")
    private int warning_num;
    @JsonProperty("create_time")
    private Date  create_time;
    @JsonProperty("create_user")
    private String create_user;
    @JsonProperty("update_user")
    private String update_user;
    @JsonProperty("update_time")
    private Date update_time;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("LAY_CHECKED")
    private boolean LAY_CHECKED;
}

