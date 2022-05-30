package com.tdh.common;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnUtils {


    public static class ReturnParam{
        public static final String success = "success";
        public static final String fail = "fail";
        public static final String fail_msg = "执行失败,请联系管理员进行问题排查";
        public static final String success_msg = "执行成功";
        public static final String exception_msg ="执行异常，请联系管理员进行处理";
        public static final String param_repeat_msg = "参数重复";
        public static final String param_repeat = "param_repeat";
        public static final String param_missing = "param_missing";
        public static final String param_missing_msg = "参数为空";
        public static final String zero = "0";
        public static final String missing = "missing";
        public static final String missing_season_index = "missing_season_index";
        public static final String missing_season_index_msg = "没有符合条件的主时令数据,请联系管理员进行处理";
        public static final String missing_season_detail_msg = "没有符合条件的次时令数据,请联系管理员进行处理";
        public static final String repeat_number = "repeat_number";
        public static final String repeat_number_msg = "号源已存在,中止操作";
        public static final String missing_season_detail = "missing_season_detail";
        public static final String missing_consumer ="missing_consumer";
        public static final String missing_consumer_msg = "当前产品为退货入库，请添加退货人信息";
        public static final String input_num_can_not_zero = "input_num_can_not_zero";
        public static final String Input_num_can_not_zero_msg = "入库数量不能为0";
        public static final String input_price_can_not_zero = "input_price_can_not_zero";
        public static final String input_price_can_not_zero_msg = "入库价格不能为0";
        public static final String missing_input_data = "missing_input_data";
        public static final String missing_input_data_msg = "缺少入库日期";
    }
    /**
     * 写入日志信息
     * @param sysTrackCode
     * @param event
     * @param msg
     */
    public static void logger(String type,String sysTrackCode, String event, String msg)  {
        File file = null;
        try {
            file = FileUtils.createFiletxt(PropertiesUtils.getProperties("log.path") + "\\"+ DateUtils.getYYYYMMDD()+"\\" + PropertiesUtils.getProperties( "log.format"));
            boolean logStatus = Boolean.parseBoolean(PropertiesUtils.getProperties("log.status"));
            if (logStatus) {
                FileUtils.info(file, DateUtils.getYYYYMMDDHHMISS(),sysTrackCode,event,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ReturnUtils.logger(Config.logClass.error,sysTrackCode,"-日志写入，异常处理 :", JSON.toJSONString(e.getMessage()));
        }
    }
    /**
     * 处理返回对象
     * @param list
     * @param count
     * @return
     */
    public static String ReturnPageObj (List<Map<String ,Object>> list, long count){
        Map<String ,Object> map  = new HashMap<>();
        map.put("data",list);
        map.put("code","0");
        map.put("count",count);
        map.put("msg","");
        String result = CommonUtil.getPrettyGsonStr(map);
        return result;
    }

    public static String ReturnPageObj (List<Map<String ,Object>> list, List<Map<String ,Object>> listMap ,long count){
        Map<String ,Object> map  = new HashMap<>();
        map.put("data",list);
        map.put("obj",listMap);
        map.put("code","0");
        map.put("count",count);
        map.put("msg","");
        String result = CommonUtil.getPrettyGsonStr(map);
        return result;
    }

    public static String ReturnObj(String code,String msg,List<Map<String,Object>> data){
        Map<String ,Object> map  = new HashMap<>();
        map.put("data",data);
        map.put("code",code);
        map.put("msg",msg);
        String result = CommonUtil.getPrettyGsonStr(map);
        return result;
    }

    public static String ReturnObj(String code,String msg,String data){
        Map<String ,Object> map  = new HashMap<>();
        map.put("data",data);
        map.put("code",code);
        map.put("msg",msg);
        String result = CommonUtil.getPrettyGsonStr(map);
        return result;
    }


    public static String ReturnObj(String code,String msg,Object obj){
        Map<String ,Object> map  = new HashMap<>();
        map.put("data",obj);
        map.put("code",code);
        map.put("msg",msg);
        String result = CommonUtil.getPrettyGsonStr(map);
        return result;
    }



}
