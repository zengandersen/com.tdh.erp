package com.tdh.pojo;


import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Dept extends QueryBean {

    private String hospital_id;

    private String compound_id;

    private String area_id;

    private String dept_id;

    private String dept_code;

    private String dept_name;

    private int order_num;

    private String status;

    private String create_user;

    private Date create_time;

    private String update_user;

    private Date update_time;

}
