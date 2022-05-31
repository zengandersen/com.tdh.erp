package com.tdh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Input  extends QueryBean {

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("factory_id")
    private String factoryId;

    @JsonProperty("goods_id")
    private String goodsId;

    @JsonProperty("meal_id")
    private String mealId;

    @JsonProperty("repertory_id")
    private String repertoryId;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("goods_code")
    private String goodsCode;

    @JsonProperty("meal_name")
    private String mealName;

    @JsonProperty("meal_code")
    private String mealCode;

    @JsonProperty("input_date")
    private String inputDate;

    @JsonProperty("input_num")
    private int inputNum;

    @JsonProperty("input_price")
    private int inputPrice;

    @JsonProperty("is_supplement")
    private Integer isSupplement;

    @JsonProperty("is_returned")
    private Integer isReturned;

    @JsonProperty("consumer_id")
    private String consumerId;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("create_user")
    private String createUser;

    @JsonProperty("update_time")
    private Date updateTime;

    @JsonProperty("update_user")
    private String updateUser;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;
}
