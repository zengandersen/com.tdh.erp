package com.tdh.pojo;

import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Role extends QueryBean {

    private String hospital_id;

    private String compound_id;

    private String role_id;

    private String role_code;

    private String role_name;

    private int order_num;

    private int status;

    private String create_user;

    private Date create_date;

    private String update_user;

    private Date update_date;
}