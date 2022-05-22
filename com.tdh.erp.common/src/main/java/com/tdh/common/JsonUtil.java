package com.tdh.common;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.http.HttpServletRequest;

public class JsonUtil {

    /**
     * 处理请求信息
     * @param request
     * @return
     * @throws Exception
     */
    public static Object toPojo(HttpServletRequest request, Object root) throws Exception{
        String data = request.getParameter("data");
        System.out.println(JSON.parseObject(data,root.getClass()));
        JSONObject object = JSONObject.fromObject(data);
        return JSONObject.toBean(object, root, new JsonConfig());
    }

    /**
     * 处理请求信息
     * @param request
     * @return
     * @throws Exception
     */
    public static Object toBean(HttpServletRequest request, Object root) throws Exception{
        String data = request.getParameter("data");
       Object obejct = JSON.parseObject(data,root.getClass());
        return obejct;
    }
}
