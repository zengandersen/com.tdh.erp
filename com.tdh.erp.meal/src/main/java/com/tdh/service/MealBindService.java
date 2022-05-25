package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.ImgUtils;
import com.tdh.mapper.MealBindMapper;
import com.tdh.mapper.MealBindResMapper;
import com.tdh.mapper.MealMapper;
import com.tdh.mapper.RepertoryMapper;
import com.tdh.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("mealBindService")
public class MealBindService extends BaseService<MealBind, MealBindMapper> {

    @Resource
    private MealBindMapper mealBindMapper;

    @Resource
    private RepertoryMapper repertoryMapper;

    @Resource
    private MealBindResMapper mealBindResMapper;
    /**
     * 获取绑定信息
     * @param mealId
     * @return
     */
    public List<Map<String ,Object>> queryMealBindInfoByMealIdServ(String mealId) throws Exception{
        List<Map<String ,Object>> result = mealBindMapper.queryMealBindInfoById(mealId);
        if(CollectionUtils.isEmpty(result)){
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     * 获取待绑定仓库数据
     * @return
     * @throws Exception
     */
    public List<MealBindRes> queryNotBIndInfo(String searchName,String mealId)throws Exception{
        List<MealBindRes> result = mealBindResMapper.queryRepertoryInfoAll(searchName);
        if(CollectionUtils.isEmpty(result)){
            result = new ArrayList<>();
        }else{
            List<Map<String ,Object>> repList = this.queryBindComplateDataByMealIdServ(mealId);
            if(!CollectionUtils.isEmpty(repList)){
                for(Map<String ,Object> repMap : repList){
                    for(MealBindRes mealBindRes : result){
                        if(String.valueOf(repMap.get("repertory_id")).equals(
                                String.valueOf(mealBindRes.getRepertory_id())
                        )){
                            mealBindRes.setLAY_CHECKED(true);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 根据套餐主索引id 删除套餐明细
     * @param mealId
     * @throws Exception
     */
    public void delMealBindByIdServ(String mealId) throws Exception{
        mealBindMapper.delMealBindById(mealId);
    }

    /**
     *  绑定信息
     * @param repertoryIds
     * @param mealId
     * @param user
     * @throws Exception
     */
    public void handleBindServ(String [] repertoryIds,String mealId,User user)throws  Exception{
        List<Map<String ,Object>> list = repertoryMapper.queryRepertoryBindInfoById(repertoryIds);
        List<MealBind> repList = new ArrayList<>();
        for(Map<String ,Object> map :list){
           MealBind  mb = new MealBind();
           mb.setMealBindId(CommonUtil.createUUIDNoFlag());
           mb.setMealId(mealId);
           mb.setRepertoryId(String.valueOf(map.get("repertory_id")));
           mb.setGoodsId(String.valueOf(map.get("goods_id")));
           mb.setCreateUser(user.getUser_code());
           this.insertServ(mb);
        }
    }

    /**
     * 插入数据
     * @param mealBind
     */
    public void insertServ(MealBind mealBind)throws Exception{
        mealBindMapper.insert(mealBind);
    }

    /**
     * 根据绑定id删除数据
     * @param mealBindId
     * @throws Exception
     */
    public void delMealBindInfoByBindIdServ(String mealBindId)throws Exception{
        mealBindMapper.delMealBindInfoByBindId(mealBindId);
    }

    /**
     * 获取已绑定数据
     * @param mealId
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryBindComplateDataByMealIdServ(String mealId) throws Exception{
        return mealBindMapper.queryBindComplateDataByMealId(mealId);
    }


}
