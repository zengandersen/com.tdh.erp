package com.tdh.service;


import cn.hutool.core.getter.ListTypeGetter;
import com.alibaba.fastjson.JSON;
import com.tdh.common.*;
import com.tdh.mapper.*;
import com.tdh.pojo.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.AbstractMapDecorator;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tdh.common.Config.sysTrackCode;


@Service("inputService")
public class InputService extends BaseService<Input, InputMapper> {

    @Resource
    private InputMapper inputMapper;

    @Resource
    private RepertoryMapper repertoryMapper;

    @Resource
    private MealBindMapper mealBindMapper;

    @Resource
    private MealMapper mealMapper;

    @Resource
    private LogMovingPinMapper logMovingPinMapper;

    @Resource
    private LogMovingMealMapper logMovingMealMapper;



    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Input params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }


    /** 新增数据
     * @param user
     * @param input
     */
    public void insertServ(User user, Input input) throws Exception {
        input.setAppId(CommonUtil.createUUIDNoFlag());
        input.setCreateUser(user.getUser_code());
        inputMapper.insert(input);
    }

    /**
     * 获取明细数据
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String ,Object> queryInputDetailInfoByIdServ(String appId) throws Exception{
        Map<String ,Object> result = inputMapper.queryInputDetailInfoById(appId);
        return result;
    }

    /**
     * 获取套餐绑定数据
     * @param goodsIds
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryMealBindInfoByIdsServ(String []goodsIds) throws Exception{
        List<Map<String,Object>> result = mealBindMapper.queryMealInfoByGoodsId(goodsIds);
        return result;
    }


    /**
     * 根据商品获取所属套餐
     * @param goodsIds
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryMealInfoByArrServ(String []goodsIds) throws Exception{
        List<Map<String,Object>> result = mealMapper.queryMealInfoByArr(goodsIds);
        return result;
    }

    /**
     * 通过
     * @param mealIdArr
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryMealInfoAndTotalByMealIdArrServ(String [] mealIdArr,List<Map<String ,Object>>repertoryList) throws Exception{
        List<Map<String ,Object>> result = mealBindMapper.queryMealInfoAndTotalByMealIdArr(mealIdArr);
        for(Map<String ,Object> repMap : repertoryList){
            for(Map<String ,Object> map : result){
                if(String.valueOf(repMap.get("repertory_id")).equals(String.valueOf(map.get("repertory_id")))){
                    map.put("total",String.valueOf(repMap.get("total")));
                }
            }
        }
        return result;
    }

    /**
     * 获取套餐数组数据
     * @param list
     * @return
     */
    public String [] queryMealInfoArr(List<Map<String ,Object>> list){
        String [] arr = new String[list.size()];
        for(int i=0; i <list.size();i++){
            arr[i] = String.valueOf(list.get(i).get("meal_id"));
        }
        return arr;
    }


    /**
     * 处理商品库存数据
     * @param object
     * @param goodsIds
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> handleRepertoryInfo(JSONObject object,String []goodsIds) throws Exception{
        List<Map<String,Object>> repertoryList = repertoryMapper.queryRepertoryInfoByGoodsId(goodsIds);

        int inputNum = Integer.parseInt(String.valueOf(object.get("input_num")));
        //处理商品库存数据，将传入的入库数量+库存现有数量
        ReturnUtils.logger(Config.logClass.truce,sysTrackCode,"-处理商品库存数据-开始", JSON.toJSONString(repertoryList));
        for(Map<String,Object> map : repertoryList){
            int total = Integer.parseInt(String.valueOf(map.get("total")));
            total = total+inputNum;
            map.put("total",total);
        }
        ReturnUtils.logger(Config.logClass.truce,sysTrackCode,"-处理商品库存数据-结束", JSON.toJSONString(repertoryList));
        return repertoryList;
    }




    public  List<Map<String ,Object>> countMealRepertory(List<Map<String ,Object>> list,String []arr) throws Exception{
        List<Map<String ,Object>> result =new ArrayList<>();
        for(String str:arr){
            int index = 0 ;
            for(Map<String ,Object> map : list){
                //相同套餐
                if(str.equals(String.valueOf(map.get("meal_id")))){
                    if(index == 0 ){
                        index = Integer.parseInt(String.valueOf(map.get("total")));
                    }
                    if(index > 0){
                        if(index >  Integer.parseInt(String.valueOf(map.get("total")))){
                            index  = Integer.parseInt(String.valueOf(map.get("total")));
                        }
                    }
                }
            }
            Map<String ,Object> map = new HashMap<>();
            map.put("meal_id",str);
            map.put("rep_totle",index);
            result.add(map);
        }
        return result;
    }

    /**
     * 拼装对象入库
     * @param repertoryList
     * @param mealNumList
     */
    public void handelInputDataServ(List<Map<String ,Object>> repertoryList ,
                                    List<Map<String ,Object>>mealNumList,
                                    List<Map<String ,Object>>mealDetailList,
                                    JSONObject object,
                                    User user)throws Exception{
        if(!CollectionUtils.isEmpty(repertoryList)){
            //更新库存数据
            for(Map<String ,Object> map : repertoryList ){
                Repertory re = new Repertory();
                re.setTotal(Integer.parseInt(String.valueOf(map.get("total"))));
                re.setRepertoryId(String.valueOf(map.get("repertory_id")));
                re.setUpdateUser(user.getUser_code());
                repertoryMapper.updateRepertoryInfoById(re);
            }
            //更新套餐数据
            for(Map<String ,Object> map : mealNumList){
                Meal meal = new Meal();
                meal.setMealId(String.valueOf(map.get("meal_id")));
                meal.setRepTotle(Integer.parseInt(String.valueOf(map.get("rep_totle"))));
                meal.setUpdateUser(user.getUser_code());
                mealMapper.updateMealById(meal);
            }
            for(Map<String ,Object> map : repertoryList){
                //入库id
                String inputId = CommonUtil.createUUIDNoFlag();
                //动账主索引id
                String pinId = CommonUtil.createUUIDNoFlag();
                //入库表新增
                Input input = handleInputParam(map,user,object,inputId);
                inputMapper.insert(input);
                //动账记录入库
                LogMovingPin lp = handleMovinePinParam(map,user,object,inputId,pinId,"");
                logMovingPinMapper.insert(lp);
                //判断该入库动账记录的商品是否有套餐， 有套餐则录入套餐记录表
                for (Map<String ,Object> mealDeMap : mealDetailList){
                    if(String.valueOf(map.get("goods_id")).equals(String.valueOf(mealDeMap.get("goods_id")))){
                        //如果有符合条件的套餐信息 则将信息写入动账套餐信息表
                        LogMovingMeal lm = handleMovingMealParam(mealDeMap,user,inputId,"",pinId);
                        logMovingMealMapper.insert(lm);
                    }
                }
            }
        }
    }

    /**
     * 入库表新增参数封装
     * @param map
     * @param user
     * @param object
     * @param inputId
     * @return
     */
    public Input handleInputParam(Map<String ,Object> map ,User user,JSONObject object,String inputId) throws Exception{
        Input input = new Input();
        input.setAppId(inputId);
        input.setFactoryId(String.valueOf(map.get("factory_id")));
        input.setGoodsId(String.valueOf(map.get("goods_id")));
        input.setRepertoryId(String.valueOf(map.get("repertory_id")));
        input.setGoodsName(String.valueOf(map.get("goods_name")));
        input.setGoodsCode(String.valueOf(map.get("goods_code")));
        input.setInputDate(String.valueOf(object.get("input_date")));
        input.setInputNum(Integer.parseInt(String.valueOf(object.get("input_num"))));
        input.setInputPrice(Integer.parseInt(String.valueOf(object.get("input_price"))));
        if(null!=object.get("supplement")&&
                Config.checkBoxStatus.on.equals(String.valueOf(object.get("supplement")))){
            input.setIsSupplement(Config.appStatus.yes);
        }else{
            input.setIsSupplement(Config.appStatus.no);
        }

        if(null!=object.get("returned")&&
                Config.checkBoxStatus.on.equals(String.valueOf(object.get("returned")))){
            input.setIsReturned(Config.appStatus.yes);
        }else{
            input.setIsReturned(Config.appStatus.no);
        }

        if(null!=object.get("consumer_id")){
            input.setConsumerId(String.valueOf(object.get("consumer_id")));
        }else{
            input.setConsumerId("");
        }

        if(null!=object.get("remark")){
            input.setRemark(String.valueOf(object.get("remark")));
        }else{
            input.setRemark("");
        }

        input.setCreateUser(user.getUser_code());
        return input;
    }

    /**
     * 动账记录入库
     * @param map
     * @param user
     * @param object
     * @param inputId
     * @param pinId
     * @return
     * @throws Exception
     */
    public LogMovingPin handleMovinePinParam(Map<String ,Object> map ,
                                             User user,JSONObject object,
                                             String inputId,String pinId,String outputId) throws Exception{
        LogMovingPin lp = new LogMovingPin();
        lp.setId(pinId);
        if(StringUtils.isEmpty(inputId)){
            lp.setInputId("");
        }else{
            lp.setInputId(inputId);
        }
        if(StringUtils.isEmpty(outputId)){
            lp.setOutputId("");
        }else{
            lp.setOutputId(outputId);
        }
        lp.setFactoryId(String.valueOf(map.get("factory_id")));
        lp.setGoodsId(String.valueOf(map.get("goods_id")));
        lp.setGoodsName(String.valueOf(map.get("goods_name")));
        lp.setGoodsCode(String.valueOf(map.get("goods_code")));
        String goodsImg = ClobUtils.handleGoodsImageObject(map);
        lp.setGoodsImg(goodsImg);
        if(null!=object.get("consumer_id")){
            lp.setConsumerId(String.valueOf(object.get("consumer_id")));
        }else{
            lp.setConsumerId("");
        }
        lp.setIsInput(Config.appStatus.yes);
        lp.setIsOutput(Config.appStatus.no);
        if(null != object.get("returned")){
            lp.setIsReturned(Config.appStatus.yes);
        }else{
            lp.setIsReturned(Config.appStatus.no);
        }
        if(null != object.get("supplement")){
            lp.setIsSupplement(Config.appStatus.yes);
        }else{
            lp.setIsSupplement(Config.appStatus.no);
        }
        if(null != object.get("is_gift")){
            lp.setIsGift(Config.appStatus.yes);
        }else{
            lp.setIsGift(Config.appStatus.no);
        }
        if(null != object.get("is_click_farming")){
            lp.setIsClickFarming(Config.appStatus.yes);
        }else{
            lp.setIsClickFarming(Config.appStatus.no);
        }
        lp.setPinDate(String.valueOf(object.get("input_date")));
        lp.setPinPrice(new BigDecimal(String.valueOf(object.get("input_price"))));
        if(null!=object.get("remark")){
            lp.setRemark(String.valueOf(object.get("remark")));
        }else{
            lp.setRemark("");
        }        lp.setCreateUser(user.getUser_code());
        return lp;
    }

    /**
     * 处理动账套餐表数据
     * @param map
     * @param user
     * @param inputId
     * @param outputId
     * @param pinId
     * @return
     */
    public LogMovingMeal handleMovingMealParam(Map<String ,Object> map , User user, String inputId, String outputId,String pinId) throws Exception{
        LogMovingMeal lm = new LogMovingMeal();
        lm.setId(CommonUtil.createUUIDNoFlag());
        lm.setPinId(pinId);
        if(StringUtils.isEmpty(inputId)){
            lm.setInputId("");
        }else{
            lm.setInputId(inputId);
        }
        if(StringUtils.isEmpty(outputId)){
            lm.setOutputId("");
        }else{
            lm.setOutputId(outputId);
        }
        lm.setMealId(String.valueOf(map.get("meal_id")));
        lm.setMealName(String.valueOf(map.get("meal_name")));
        lm.setMealCode(String.valueOf(map.get("meal_code")));
        String mealImg = ClobUtils.handleMealmageObject(map);
        lm.setMealImg(mealImg);
        lm.setCreateUser(user.getUser_code());
        return lm;
    }

}
