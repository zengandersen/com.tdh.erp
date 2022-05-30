package com.tdh.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class LogMovingMeal extends QueryBean {

    @JsonProperty("id")
    private String id;

    @JsonProperty("pin_id")
    private String pinId;

    @JsonProperty("input_id")
    private String inputId;

    @JsonProperty("output_id")
    private String outputId;

    @JsonProperty("meal_id")
    private String mealId;

    @JsonProperty("meal_name")
    private String mealName;

    @JsonProperty("meal_code")
    private String mealCode;

    @JsonProperty("meal_img")
    private String mealImg;

    @JsonProperty("create_user")
    private String createUser;

    @JsonProperty("create_time")
    private Date createTime;
}
