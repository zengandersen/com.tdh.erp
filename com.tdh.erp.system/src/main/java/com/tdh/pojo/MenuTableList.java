package com.tdh.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class MenuTableList {

    private String id;
    private String parent_title_code;
    private String pid;
    private String title_name;
    private String href;
    private String image;
    private String icon;
    private String target;
    private String create_by;
    private Date create_time;
    private String update_by;
    private Date update_time;
    private String status;
    private String level_flag;
    private Integer id_no;
    private Integer pid_no;
    private boolean LAY_CHECKED;
}
