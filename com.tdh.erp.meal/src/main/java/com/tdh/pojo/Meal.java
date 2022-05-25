package com.tdh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Meal extends QueryBean {

    @JsonProperty("meal_id")
    private String mealId;

    @JsonProperty("meal_code")
    private String mealCode;

    @JsonProperty("meal_name")
    private String mealName;

    @JsonProperty("meal_spec")
    private String mealSpec;

    @JsonProperty("meal_price")
    private int mealPrice;

    @JsonProperty("rep_totle")
    private int repTotle;

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("create_user")
    private String createUser;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("update_user")
    private String updateUser;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("meal_img")
    private String mealImg;
}
