package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Meal;
import com.tdh.pojo.MealBind;
import com.tdh.pojo.MealBindRes;
import com.tdh.pojo.User;
import com.tdh.service.GoodsService;
import com.tdh.service.MealBindService;
import com.tdh.service.MealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class MealBindController extends BaseController {


    @Resource
    private MealBindService mealBindService;

    @Resource
    private GoodsService goodsService;


    /**
     * 获取待绑定列表信息
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = Route.MealBindUrl.QUERY_NOT_BIND_LIST, method = {RequestMethod.POST})
    public String queryNotBind(HttpServletRequest request) {
        try {
            String mealId = request.getParameter("meal_id");
            String searchField = request.getParameter(Config.searchField);
            List<MealBindRes> resList =   mealBindService.queryNotBIndInfo(searchField,mealId);
            Map<String ,Object> map  = new HashMap<>();
            map.put("data",resList);
            map.put("code","0");
            map.put("count",0);
            map.put("msg","");
            String result = CommonUtil.getPrettyGsonStr(map);
            return result;
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


    /**
     * 获取已绑定列表信息
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = Route.MealBindUrl.QUERY_LIST, method = {RequestMethod.POST})
    public String list(HttpServletRequest request) {
        try {
            String mealId = request.getParameter("meal_id");
            List<Map<String,Object>> list = mealBindService.queryMealBindInfoByMealIdServ(mealId);
            goodsService.handleListImageData(list);
            return ReturnUtils.ReturnPageObj(list,0);
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


    /**
     * 套餐绑定
     * @param request
     * @return
     */
    @RequestMapping(value = Route.MealBindUrl.TO_DO_BIND, method = {RequestMethod.POST})
    public String bind(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            String mealId= request.getParameter("meal_id");
            String [] repertoryIds = String.valueOf(request.getParameter("repertory_ids")).split(",");
            mealBindService.delMealBindByIdServ(mealId);
            mealBindService.handleBindServ(repertoryIds,mealId,user);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg,"");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.MealBindUrl.DEL_BIND, method = {RequestMethod.POST})
    public String del(HttpServletRequest request) {
        try {
            mealBindService.delMealBindInfoByBindIdServ(request.getParameter("meal_bind_id"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

}


