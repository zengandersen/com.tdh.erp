package com.tdh.controller;


import com.alibaba.fastjson.JSON;
import com.tdh.common.*;
import com.tdh.pojo.Input;
import com.tdh.pojo.Repertory;
import com.tdh.pojo.User;
import com.tdh.service.GoodsService;
import com.tdh.service.InputService;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.tdh.common.Config.sysTrackCode;


@RestController
public class InputController extends BaseController {

    @Resource
    private InputService inputService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private RepertoryService repertoryService;


    @RequestMapping(value = Route.InputUrl.QUERY_LIST,method = {RequestMethod.POST})
    public String list(HttpServletRequest request,Input params){
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            params.setMealName(searchField);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = inputService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(), pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    @RequestMapping(value = Route.InputUrl.ADD_SINGLE_GOODS,method = {RequestMethod.POST})
    @Transactional
    public String add(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            String data = request.getParameter("data");
            JSONObject object = JSONObject.fromObject(data);
            //参数校验
            if(!ReturnUtils.ReturnParam.success.equals(this.addVerify(object))){
                return this.addVerify(object);
            }
            String [] goodsIds = String.valueOf(object.get("goods_ids")).split(",");
            String [] consumerIds = String.valueOf(object.get("consumer_ids")).split(",");

            ReturnUtils.logger(Config.logClass.truce,sysTrackCode,"-开始处理入库操作业务-开始",
                    "入库商品信息 :"+JSON.toJSONString(goodsIds)
                    +"入库仓库信息:"+JSON.toJSONString(consumerIds));
            //根据传入信息变更库存数量(repertoryList 该集合已经变更了库存数量)
            List<Map<String ,Object>> repertoryList = inputService.handleRepertoryInfo(object,goodsIds);
            //获取到对应商品的套餐信息
            List<Map<String ,Object>> list = inputService.queryMealInfoByArrServ(goodsIds);
            //将获取的套餐信息 提取套餐id 放入数组
            String [] arr = inputService.queryMealInfoArr(list);
            //通过套餐id 的数组获取相关绑定套餐明细数据 并替换套餐库存数量
            List<Map<String ,Object>>mealDetailList =  inputService.queryMealInfoAndTotalByMealIdArrServ(arr,repertoryList);
            //获取每一个套餐的动账之后的库存数量
            List<Map<String ,Object>>mealNumList=inputService.countMealRepertory(mealDetailList,arr);
            //入库操作
            inputService.handelInputDataServ(repertoryList,mealNumList,mealDetailList,object,user);
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
        if(StringUtils.isEmpty(String.valueOf(object.get("input_num")))){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.input_num_can_not_zero, ReturnUtils.ReturnParam.input_price_can_not_zero_msg, "");
        }
        if(StringUtils.isEmpty(String.valueOf(object.get("input_price")))){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.input_num_can_not_zero, ReturnUtils.ReturnParam.input_price_can_not_zero_msg, "");
        }
        if(StringUtils.isNotEmpty((String.valueOf(object.get("returned"))))&&StringUtils.isEmpty(String.valueOf(object.get("consumer_id")))){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.missing_consumer, ReturnUtils.ReturnParam.missing_consumer_msg, "");
        }
        if(StringUtils.isEmpty(String.valueOf(object.get("input_num")))){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.input_num_can_not_zero, ReturnUtils.ReturnParam.input_price_can_not_zero_msg, "");
        }
        if(StringUtils.isEmpty(String.valueOf(object.get("input_date")))){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.missing_input_data, ReturnUtils.ReturnParam.missing_input_data_msg, "");
        }
        return ReturnUtils.ReturnParam.success;
    }


    /**
     * 获取入库明细
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.InputUrl.QUERY_DETAIL, method = {RequestMethod.POST})
    public String queryDetail(HttpServletRequest request) {
        try {
            String inputId = request.getParameter("detail_id");
            if (StringUtils.isEmpty(inputId)) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "create");
            }
            Map<String, Object> map = inputService.queryInputDetailInfoByIdServ(inputId);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, map);

        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

}


