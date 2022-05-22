package com.tdh.pojo;


import com.tdh.common.QueryBean;
import lombok.Data;

@Data
public class Dict extends QueryBean {

    private String hospital_id;
    private String dict_type_code;
    private String dict_id;
    private String dict_name;
    private String dict_code;
    private int order_num;
    private int status;
    private String create_by;
    private String create_time;
    private String update_by;
    private String update_time;



}
