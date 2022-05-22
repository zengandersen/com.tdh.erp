package com.tdh.pojo;

import com.tdh.common.QueryBean;
import lombok.Data;

import java.util.Date;

@Data
public class OaFtp extends QueryBean {


            private String ip;

            private int port;

            private String name;

            private String pwd;
}
