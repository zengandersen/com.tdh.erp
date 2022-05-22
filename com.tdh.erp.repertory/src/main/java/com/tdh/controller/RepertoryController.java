package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Goods;
import com.tdh.pojo.Repertory;
import com.tdh.pojo.User;

import com.tdh.service.GoodsService;
import com.tdh.service.RepertoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RepertoryController extends BaseController {

    @Resource
    private RepertoryService repertoryService;

    @Resource
    private GoodsService goodsService;

    @RequestMapping(value = Route.RepertoryUrl.QUERY_LIST, method = {RequestMethod.POST})
    public String list(HttpServletRequest request, Repertory params) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            String searchField = request.getParameter(Config.searchField);
            params.setGoodsId(searchField);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = repertoryService.selectPageMap(params);
            goodsService.handlePageListImageData(pageList);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(), pageList.getCount());
            return result;
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 新增仓库数据
     * @param request
     * @return
     */
    @RequestMapping(value = Route.RepertoryUrl.ADD,method = {RequestMethod.POST})
    public String add (HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            Repertory param = (Repertory) JsonUtil.toBean(request, new Repertory());
            repertoryService.insertServ(user,param);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    @RequestMapping(value = Route.RepertoryUrl.QUERY_DETAIL,method = {RequestMethod.POST})
    public String queryDetail(HttpServletRequest request){
        try{
            String repertoryId = request.getParameter("detail_id");
            if (StringUtils.isEmpty(repertoryId)) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "create");
            }
            Map<String, Object> map = repertoryService.queryRepertoryDetailByIdServ(repertoryId);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, map);

        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


    @RequestMapping(value = Route.RepertoryUrl.EDIT, method = {RequestMethod.POST})
    public String edit(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            Repertory param = (Repertory) JsonUtil.toBean(request, new Repertory());
            Map<String ,Object> goodsMap = goodsService.queryGoodsDetailByIdServ(param.getGoodsId());
            param.setGoodsName(String.valueOf(goodsMap.get("goods_name")));
            repertoryService.updateServ(user, param);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
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
    @RequestMapping(value = Route.RepertoryUrl.DEL, method = {RequestMethod.POST})
    public String del(HttpServletRequest request) {
        try {
            repertoryService.delServ(request.getParameter("repertory_id"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }



}


