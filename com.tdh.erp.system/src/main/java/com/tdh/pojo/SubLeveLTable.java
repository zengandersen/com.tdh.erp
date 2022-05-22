package com.tdh.pojo;


import lombok.Data;

import java.util.List;

@Data
public class SubLeveLTable {

    private String id;
    private String parent_id;
    private String title;
    private String icon;
    private String href;
    private String target;
    private String level;
    private List<SubLeveLTable> child;
    private boolean LAY_CHECKED;
}
