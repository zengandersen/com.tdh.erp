package com.tdh.controller;


import com.alibaba.fastjson.JSON;
import com.tdh.common.*;
import com.tdh.pojo.Input;
import com.tdh.pojo.Output;
import com.tdh.pojo.User;
import com.tdh.service.GoodsService;
import com.tdh.service.InputService;
import com.tdh.service.OutputService;
import com.tdh.service.RepertoryService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.tdh.common.Config.sysTrackCode;


@RestController
public class OutputController extends BaseController {

    @Resource
    private OutputService outputService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private RepertoryService repertoryService;


    @RequestMapping(value = Route.OutUrl.QUERY_LIST,method = {RequestMethod.POST})
    public String list(HttpServletRequest request,Output params){
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            String startDate = request.getParameter(Config.searchStartDate);
            String endDate = request.getParameter(Config.searchEndDate);
            String factoryId =request.getParameter(Config.searchSelectOne);
            String clickFarming = request.getParameter(Config.searchSelectTwo);
            String gift = request.getParameter(Config.searchSelectThree);
            params.setGoodsName(searchField);
            params.setStartDate(startDate);
            params.setEndDate(endDate);
            params.setFactoryId(factoryId);
            if(StringUtils.isNotEmpty(clickFarming)){
                params.setIsClickFarming(Integer.parseInt(String.valueOf(clickFarming)));
            }
            if(StringUtils.isNotEmpty(gift)){
                params.setIsGift(Integer.parseInt(String.valueOf(gift)));
            }
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = outputService.selectPageMap(params);
            goodsService.handlePageListImageData(pageList);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(), pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }



}


