package com.tdh.pojo;

import com.tdh.common.QueryBean;
import lombok.Data;

@Data
public class Hospital extends QueryBean {


    private String hospital_id;
    private String hospital_code;
    private String hospital_name;
    private String contacts;
    private String contact_tel;
    private String contact_addr;
    private String status;
    private String create_user;
    private String create_time;
    private String update_user;
    private String update_time;
}
