package com.tdh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LogMovingPin extends QueryBean{

    @JsonProperty("id")
    private String id;

    @JsonProperty("input_id")
    private String inputId;

    @JsonProperty("input_app_no")
    private String inputAppNo;

    @JsonProperty("output_id")
    private String outputId;

    @JsonProperty("output_app_no")
    private String outputAppNo;

    @JsonProperty("factory_id")
    private String factoryId;


    @JsonProperty("goods_id")
    private String goodsId;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("goods_code")
    private String goodsCode;

    @JsonProperty("goods_img")
    private String goodsImg;


    @JsonProperty("consumer_id")
    private String consumerId;

    @JsonProperty("is_input")//是否为进货
    private int isInput;

    @JsonProperty("is_output")//是否为出库
    private int isOutput;

    @JsonProperty("is_returned")//是否为退货
    private int isReturned;

    @JsonProperty("is_supplement")//是否为补单
    private int isSupplement;

    @JsonProperty("is_gift")//是否为赠品
    private int isGift;

    @JsonProperty("is_click_farming")//是否为刷单
    private int isClickFarming;

    @JsonProperty("pin_date")
    private String pinDate;

    @JsonProperty("pin_price")
    private BigDecimal pinPrice;

    @JsonProperty("remark")
    private String  remark;

    @JsonProperty("create_user")
    private String  createUser;

    @JsonProperty("create_time")
    private Date  createTime;

}
