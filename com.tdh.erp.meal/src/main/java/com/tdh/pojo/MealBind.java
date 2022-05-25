package com.tdh.pojo;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class MealBind extends QueryBean {


    @JsonProperty("meal_bind_id")
    private String mealBindId;

    @JsonProperty("meal_id")
    private String mealId;

    @JsonProperty("repertory_id")
    private String repertoryId;

    @JsonProperty("goods_id")
    private String goodsId;

    @JsonProperty("create_time")
    private Date  createTime;

    @JsonProperty("create_user")
    private String createUser;

    @JsonProperty("update_user")
    private String updateUser;

    @JsonProperty("update_time")
    private Date updateTime;

}
