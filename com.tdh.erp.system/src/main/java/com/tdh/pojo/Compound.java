package com.tdh.pojo;

import com.tdh.common.QueryBean;
import lombok.Data;

@Data
public class Compound extends QueryBean {

    private String hospital_id;
    private String compound_id;
    private String compound_code;
    private String compound_name;
    private String contacts;
    private String contact_tel;
    private String contact_addr;
    private String status;
    private int order_num;
    private String create_user;
    private String create_time;
    private String update_user;
    private String update_time;
}
