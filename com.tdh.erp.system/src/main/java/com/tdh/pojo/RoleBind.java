package com.tdh.pojo;

import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class RoleBind extends QueryBean {
    private String id;

    private String role_id;

    private String menu_id;

    private String create_user;

    private Date create_date;

    private String update_user;

    private Date update_date;
}