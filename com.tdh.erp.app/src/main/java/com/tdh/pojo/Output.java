package com.tdh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Output extends QueryBean {


    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("app_no")
    private String appNo;

    @JsonProperty("factory_id")
    private String factoryId;

    @JsonProperty("goods_id")
    private String goodsId;

    @JsonProperty("repertory_id")
    private String repertoryId;

    @JsonProperty("consumer_id")
    private String consumerId;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("goods_code")
    private String goodsCode;

    @JsonProperty("output_date")
    private String outputDate;

    @JsonProperty("output_num")
    private int outputNum;

    @JsonProperty("output_price")
    private BigDecimal outputPrice;

    @JsonProperty("is_click_farming")
    private Integer isClickFarming;

    @JsonProperty("is_gift")
    private Integer isGift;

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
