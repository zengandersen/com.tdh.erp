package com.tdh.service;


import com.alibaba.fastjson.JSON;
import com.tdh.common.*;
import com.tdh.mapper.*;
import com.tdh.pojo.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tdh.common.Config.sysTrackCode;


@Service("outputService")
public class OutputService extends BaseService<Output, OutputMapper> {

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

    @Resource
    private InputService inputService;

    @Resource
    private OutputMapper outputMapper;



    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Output params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
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

        int outputNum = Integer.parseInt(String.valueOf(object.get("output_num")));
        //处理商品库存数据，将传入的库存现有数量 - 出库数量
        for(Map<String,Object> map : repertoryList){
            int total = Integer.parseInt(String.valueOf(map.get("total")));
            total = total-outputNum;
            map.put("total",total);
        }
        return repertoryList;
    }


    /**
     * 拼装对象入库
     * @param repertoryList
     * @param mealNumList
     */
    public void handleOutputDataServ(List<Map<String ,Object>> repertoryList ,
                                    List<Map<String ,Object>>mealNumList,
                                    List<Map<String ,Object>>mealDetailList,
                                    JSONObject object,
                                    User user)throws Exception{
        if(!CollectionUtils.isEmpty(repertoryList)){
            String appNo = CommonUtil.createAppno(CommonUtil.generateUID());
            inputService.insertRepertory(repertoryList,user);
            inputService.insertMealDetail(mealDetailList,user);
            this.insertOutput(repertoryList,mealDetailList,user,object,appNo);
        }
    }


    /**
     * 入库处理
     * @param repertoryList
     * @param mealDetailList
     * @throws Exception
     */
    public void insertOutput (List<Map<String ,Object>> repertoryList,
                             List<Map<String ,Object>> mealDetailList,
                             User user,JSONObject object,String appNo) throws Exception {
        for(Map<String ,Object> map : repertoryList){
            //入库id
            String outputId = CommonUtil.createUUIDNoFlag();
            //动账主索引id
            String pinId = CommonUtil.createUUIDNoFlag();
            //入库表新增
            Output output= this.handleOutputParam(map,user,object,outputId,appNo);
            outputMapper.insert(output);
            //动账记录入库
            LogMovingPin lp = inputService.handleMovinePinParam(map,user,object,"",pinId,outputId,appNo);
            logMovingPinMapper.insert(lp);
            //判断该入库动账记录的商品是否有套餐， 有套餐则录入套餐记录表
            for (Map<String ,Object> mealDeMap : mealDetailList){
                if(String.valueOf(map.get("goods_id")).equals(String.valueOf(mealDeMap.get("goods_id")))){
                    //如果有符合条件的套餐信息 则将信息写入动账套餐信息表
                    LogMovingMeal lm =inputService.handleMovingMealParam(mealDeMap,user,"",outputId,pinId);
                    logMovingMealMapper.insert(lm);
                }
            }
        }
    }

    /**
     * 处理入库对象
     * @param map
     * @param user
     * @param object
     * @param outputId
     * @param appNo
     * @return
     * @throws Exception
     */
    public Output handleOutputParam(Map<String ,Object> map ,User user,JSONObject object,String outputId,String appNo)throws Exception{
        Output output = new Output();
        output.setAppId(outputId);
        output.setAppNo(appNo);
        output.setFactoryId(String.valueOf(map.get("factory_id")));
        output.setGoodsId(String.valueOf(map.get("goods_id")));
        output.setRepertoryId(String.valueOf(map.get("repertory_id")));
        if(null!=object.get("consumer_id")){
            output.setConsumerId(String.valueOf(object.get("consumer_id")));
        }else{
            output.setConsumerId("");
        }
        output.setGoodsName(String.valueOf(map.get("goods_name")));
        output.setGoodsCode(String.valueOf(map.get("goods_code")));
        output.setOutputDate(String.valueOf(object.get("output_date")));
        output.setOutputNum(Integer.parseInt(String.valueOf(object.get("output_num"))));
        output.setOutputPrice(new BigDecimal(Integer.parseInt(String.valueOf(object.get("output_price")))));
        if(null!=object.get("clickFarming")&&
                Config.checkBoxStatus.on.equals(String.valueOf(object.get("clickFarming")))){
            output.setIsClickFarming(Config.appStatus.yes);
        }else{
            output.setIsClickFarming(Config.appStatus.no);
        }
        if(null!=object.get("gift")&&
                Config.checkBoxStatus.on.equals(String.valueOf(object.get("gift")))){
            output.setIsGift(Config.appStatus.yes);
        }else{
            output.setIsGift(Config.appStatus.no);
        }
        if(null!=object.get("remark")){
            output.setRemark(String.valueOf(object.get("remark")));
        }else{
            output.setRemark("");
        }
        output.setCreateUser(user.getUser_code());
        return output;
    }


}
