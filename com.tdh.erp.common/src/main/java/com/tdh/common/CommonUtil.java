package com.tdh.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {



    public static String creatUUID() {
        return UUID.randomUUID().toString();
    }

    public static String createUUIDNoFlag(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String generateUID(){
        Random random = new Random();
        String result="";
        for(int i=0;i<8;i++){
            //首字母不能为0
            result += (random.nextInt(9)+1);
        }
        return result;
    }

    public static String createAppno(String generateUID){
        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return date+"-"+generateUID ;
    }

    /**
     * json格式美化
     * @param object
     * @return
     */
    public static String getPrettyGsonStr(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }


    public static int getPage(HttpServletRequest request){
        return Integer.parseInt(request.getParameter(Config.page));
    }

    public static int getLimit(HttpServletRequest request){
        return Integer.parseInt(request.getParameter(Config.limit));
    }

    public static String getSearchField(HttpServletRequest request){
        return request.getParameter(Config.searchField);
    }


    public static String generateRandomArray(int num) {
        String chars = "0123456789";
        char[] rands = new char[num];
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            rands[i] = chars.charAt(rand);
        }
        String result =new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+"-"+String.valueOf(rands);
        return result;
    }

    public static String handleTimeTrival(int a){
        String result = "";
        if (a > 6 && a <= 12) {
            result = "AM";
        }
        if (a > 12 && a <= 13) {
            result = "ZW";
        }
        if (a > 13 && a <= 18) {
            result = "PM";
        }
        return  result;
    }


}
