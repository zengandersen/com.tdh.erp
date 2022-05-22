package com.tdh.pojo;


import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class Menu  extends QueryBean {
    private String id;
    private String parent_title_code;
    private String title_name;
    private String href;
    private String image;
    private String icon;
    private String target;
    private String status;
    private String create_by;
    private Date create_time;
    private String update_by;
    private Date update_time;
    private String level_flag;
    private String parent_id;
    private boolean LAY_CHECKED;

}
