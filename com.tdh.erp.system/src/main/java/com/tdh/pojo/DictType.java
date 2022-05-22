package com.tdh.pojo;


import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class DictType extends QueryBean {
    private String hospital_id;
    private String id;
    private String dict_type_code;
    private String dict_type_name;
    private int order_num;
    private String create_by;
    private Date create_time;
    private String update_by;
    private Date update_time;
}
