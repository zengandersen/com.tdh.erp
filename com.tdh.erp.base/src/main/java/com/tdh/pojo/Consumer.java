package com.tdh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Consumer extends QueryBean{

    @JsonProperty("consumer_id")
    private String consumerId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sex")
    private int sex;

    @JsonProperty("tel")
    private String tel;

    @JsonProperty("province")
    private String province;

    @JsonProperty("addr")
    private String addr;

    @JsonProperty("repeat_purchase_rate")
    private int repeatPurchaseRate;

    @JsonProperty("is_return")
    private int isReturn;

    @JsonProperty("is_friend")
    private int isFriend;

    @JsonProperty("is_black")
    private int isBlack;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("create_user")
    private String createUser;

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("update_user")
    private String updateUser;

    @JsonProperty("update_time")
    private Date updateTime;

    @JsonProperty("return")
    private String Return;

    @JsonProperty("friend")
    private String Friend;

    @JsonProperty("black")
    private String Black;
}
