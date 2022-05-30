package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Meal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface MealMapper extends BaseMapper<Meal> {

    public Map<String, Object> queryMealInfoById(@Param("mealId") String mealId);

    public void updateMealById(Meal meal);

    public void delMealById(@Param("mealId") String mealId);

    public List<Map<String ,Object>> queryMealInfoByArr(@Param("goodsIds") String [] goodsIds);

}