package com.tdh.controller;


import com.alibaba.fastjson.JSON;
import com.tdh.common.*;
import com.tdh.pojo.Input;
import com.tdh.pojo.Repertory;
import com.tdh.pojo.User;
import com.tdh.service.GoodsService;
import com.tdh.service.InputService;
import com.tdh.service.RepertoryService;
import freemarker.template.utility.StringUtil;
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
            String startDate = request.getParameter(Config.searchStartDate);
            String endDate = request.getParameter(Config.searchEndDate);
            String factoryId =request.getParameter(Config.searchSelectOne);
            String supplement = request.getParameter(Config.searchSelectTwo);
            String returned = request.getParameter(Config.searchSelectThree);
            params.setGoodsName(searchField);
            params.setStartDate(startDate);
            params.setEndDate(endDate);
            params.setFactoryId(factoryId);
            if(StringUtils.isNotEmpty(supplement)){
                params.setIsSupplement(Integer.parseInt(String.valueOf(supplement)));
            }
            if(StringUtils.isNotEmpty(returned)){
                params.setIsReturned(Integer.parseInt(String.valueOf(returned)));
            }
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = inputService.selectPageMap(params);
            goodsService.handlePageListImageData(pageList);
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
            //????????????
            if(!ReturnUtils.ReturnParam.success.equals(this.addVerify(object))){
                return this.addVerify(object);
            }
            String [] goodsIds = String.valueOf(object.get("goods_ids")).split(",");
            String [] consumerIds = String.valueOf(object.get("consumer_ids")).split(",");

            ReturnUtils.logger(Config.logClass.truce,sysTrackCode,"-??????????????????????????????-??????",
                    "?????????????????? :"+JSON.toJSONString(goodsIds)
                    +"??????????????????:"+JSON.toJSONString(consumerIds));
            //????????????????????????????????????(repertoryList ????????????????????????????????????)
            List<Map<String ,Object>> repertoryList = inputService.handleRepertoryInfo(object,goodsIds);
            //????????????????????????????????????
            List<Map<String ,Object>> list = inputService.queryMealInfoByArrServ(goodsIds);
            //???????????????????????? ????????????id ????????????
            String [] arr = inputService.queryMealInfoArr(list);
            //????????????id ????????????????????????????????????????????? ???????????????????????????
            List<Map<String ,Object>>mealDetailList =  inputService.queryMealInfoAndTotalByMealIdArrServ(arr,repertoryList);
            //???????????????????????????????????????????????????
            List<Map<String ,Object>>mealNumList=inputService.countMealRepertory(mealDetailList,arr);
            //????????????
            inputService.handelInputDataServ(repertoryList,mealNumList,mealDetailList,object,user);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * ??????????????????
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
        if(null != object.get("returned")&&StringUtils.isEmpty(String.valueOf(object.get("consumer_id")))){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.missing_consumer, ReturnUtils.ReturnParam.missing_consumer_msg, "");
        }
        if(null == object.get("returned") && null == object.get("supplement")){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.missing_property, ReturnUtils.ReturnParam.missing_property_msg, "");
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
     * ??????????????????
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


