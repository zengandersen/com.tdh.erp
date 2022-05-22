package com.tdh.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdh.common.QueryBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Goods extends QueryBean {


    @JsonProperty("goods_id")
    private String goodsId;

    @JsonProperty("factory_id")
    private String factoryId;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("goods_code")
    private String goodsCode;

    @JsonProperty("pinyin")
    private String pinyin;

    @JsonProperty("Ename")
    private String Ename;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("goods_img")
    private String goodsImg;

    @JsonProperty("purch_price")
    private BigDecimal purchPrice;

    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("create_user")
    private String createUser;

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("update_user")
    private String updateUser;

    @JsonProperty("update_time")
    private Date updateTime;
}
