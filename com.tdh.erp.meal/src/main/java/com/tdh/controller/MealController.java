package com.tdh.controller;


import com.tdh.common.*;


import com.tdh.pojo.Meal;
import com.tdh.pojo.User;
import com.tdh.service.MealBindService;
import com.tdh.service.MealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class MealController extends BaseController {


    @Resource
    private MealService mealService;

    @Resource
    private MealBindService mealBindService;
    /**
     * 获取列表信息
     *
     * @param request
     * @param params
     * @return
     */
    @RequestMapping(value = Route.MealUrl.QUERY_LIST, method = {RequestMethod.POST})
    public String list(HttpServletRequest request, Meal params) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            String searchField = request.getParameter(Config.searchField);
            params.setMealName(searchField);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = mealService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(), pageList.getCount());
            return result;
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


    /**
     * 新增
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.MealUrl.ADD, method = {RequestMethod.POST})
    public String add(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            Meal param = (Meal) JsonUtil.toBean(request, new Meal());
            mealService.insertServ(user, param);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 获取套餐明细
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.MealUrl.QUERY_INFO, method = {RequestMethod.POST})
    public String queryDetail(HttpServletRequest request) {
        try {
            String mealId = request.getParameter("detail_id");
            if (StringUtils.isEmpty(mealId)) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "create");
            }
            Map<String, Object> map = mealService.queryMealInfoByIdServ(mealId);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, map);

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
    @RequestMapping(value = Route.MealUrl.DEL, method = {RequestMethod.POST})
    public String del(HttpServletRequest request) {
        try {
            mealService.delInfoServ(request.getParameter("meal_id"));
            mealBindService.delMealBindByIdServ(request.getParameter("meal_id"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.MealUrl.EDIT, method = {RequestMethod.POST})
    public String edit(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            Meal param = (Meal) JsonUtil.toBean(request, new Meal());
            mealService.updateInfoServ(user, param);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


}


