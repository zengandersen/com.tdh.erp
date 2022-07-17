package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.PageList;
import com.tdh.mapper.MealBindMapper;
import com.tdh.mapper.MealBindResMapper;
import com.tdh.pojo.MealBindRes;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("mealBindResService")
public class MealBindResService extends BaseService<MealBindRes, MealBindResMapper> {

    @Resource
    private MealBindMapper mealBindMapper;


    public PageList<Map<String, Object>> selectPageMap(MealBindRes params) throws Exception {
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 获取待绑定仓库数据
     * @return
     * @throws Exception
     */
    public PageList<Map<String, Object>> queryNotBIndInfo(PageList<Map<String, Object>>pageList, String mealId)throws Exception{
        if(!CollectionUtils.isEmpty(pageList.getDataList())){
                if(StringUtils.isEmpty(mealId)){
                    mealId = "";
                }
                List<Map<String ,Object>> repList = this.queryBindComplateDataByMealIdServ(mealId);
                if(!CollectionUtils.isEmpty(repList)){
                    for(Map<String ,Object> repMap : repList){
                        for(Map<String ,Object> map : pageList.getDataList()){
                            if(String.valueOf(repMap.get("repertory_id")).equals(
                                String.valueOf(map.get("repertory_id"))
                            )){
                                map.put("LAY_CHECKED",true);
                            }
                        }
                    }
                }else{
                    return pageList;
                }
        }
        return pageList;
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
