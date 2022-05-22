package com.tdh.pojo;

import com.tdh.common.QueryBean;

import lombok.Data;

@Data
public class User extends QueryBean {

    private String hospital_id;
    private String user_id;
    private String user_code;
    private String user_name;
    private String user_pwd;
    private String status;
    private String create_by;
    private String create_time;
    private String role_id;
    private String sex;
    private String job_number;
    private String photo;
    private String user_class;
    private String update_by;
    private String update_time;
    private String compound_id;
    private String input_password;
}
