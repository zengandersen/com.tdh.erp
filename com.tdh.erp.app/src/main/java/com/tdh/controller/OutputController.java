package com.tdh.controller;


import com.alibaba.fastjson.JSON;
import com.tdh.common.*;
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

    @Resource
    private InputService inputService;


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
            String appNo = request.getParameter(Config.searchAppNo);
            params.setGoodsName(searchField);
            params.setAppNo(appNo);
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

    @RequestMapping(value = Route.OutUrl.ADD_SINGLE_GOODS,method = {RequestMethod.POST})
    @Transactional
    public String add(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONObject object = JSONObject.fromObject(data);
            //参数校验
            if(!ReturnUtils.ReturnParam.success.equals(this.addVerify(object))){
                return this.addVerify(object);
            }
            String [] goodsIds = String.valueOf(object.get("goods_ids")).split(",");
            String [] consumerIds = String.valueOf(object.get("consumer_ids")).split(",");
            ReturnUtils.logger(Config.logClass.truce,sysTrackCode,"-开始处理出库操作业务-开始",
                    "出库商品信息 :"+JSON.toJSONString(goodsIds)
                            +"出库仓库信息:"+JSON.toJSONString(consumerIds));
            List<Map<String ,Object>> repertoryList = outputService.handleRepertoryInfo(object,goodsIds);
            //获取到对应商品的套餐信息
            List<Map<String ,Object>> list = inputService.queryMealInfoByArrServ(goodsIds);
            //将获取的套餐信息 提取套餐id 放入数组
            String [] arr = inputService.queryMealInfoArr(list);
            //通过套餐id 的数组获取相关绑定套餐明细数据 并替换套餐库存数量
            List<Map<String ,Object>>mealDetailList =  inputService.queryMealInfoAndTotalByMealIdArrServ(arr,repertoryList);
            //获取每一个套餐的动账之后的库存数量
            List<Map<String ,Object>>mealNumList=inputService.countMealRepertory(mealDetailList,arr);
            //入库操作
            outputService.handleOutputDataServ(repertoryList,mealNumList,mealDetailList,object,user);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 入库参数校验
     * @param object
     * @return
     */
    private String addVerify( JSONObject object){
        if(StringUtils.isEmpty(String.valueOf(object.get("output_num")))){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.output_num_can_not_zero, ReturnUtils.ReturnParam.output_num_can_not_zero_msg, "");
        }
        if(null == object.get("consumer_id")){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.missing_output_consumer, ReturnUtils.ReturnParam.missing_output_consumer_msg, "");
        }
        if(null == object.get("output_date")){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.missing_output_date, ReturnUtils.ReturnParam.missing_output_date_msg, "");
        }
        return ReturnUtils.ReturnParam.success;
    }
}


