package com.tdh.pojo;


import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Area extends QueryBean {

    private String hospital_id;

    private String compound_id;

    private String area_id;

    private String area_code;

    private String area_name;

    private String area_position;

    private String create_user;

    private Date create_time;

    private String update_user;

    private Date update_time;

    private int order_num;

    private String status;
}
