package com.tdh.pojo;


import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MenuChild extends QueryBean {
    private String id;
    private String parent_id;
    private String title_name;
    private String href;
    private String icon;
    private String target;
    private String status;
    private String level_flag;
    private String create_by;
    private Date create_time;
    private String update_by;
    private Date update_time;
    private List<MenuChild> child;

}
