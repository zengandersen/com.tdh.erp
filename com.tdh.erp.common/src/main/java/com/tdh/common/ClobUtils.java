package com.tdh.common;

import java.sql.Clob;
import java.util.Map;

public class ClobUtils {

    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static String handleGoodsImageObject(Map<String ,Object> map )throws Exception{
        String goods_img = ImgUtils.clobToString((Clob) map.get("goods_img"));
        return goods_img;
    }

    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static String handleMealmageObject(Map<String ,Object> map )throws Exception{
        String goods_img = ImgUtils.clobToString((Clob) map.get("meal_img"));
        return goods_img;
    }
}
