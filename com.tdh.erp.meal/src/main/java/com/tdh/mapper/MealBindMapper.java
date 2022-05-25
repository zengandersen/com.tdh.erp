package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Meal;
import com.tdh.pojo.MealBind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface MealBindMapper extends BaseMapper<MealBind> {

    public List<Map<String ,Object>> queryMealBindInfoById (@Param("mealId") String mealId);

    public void delMealBindById(@Param("mealId") String mealId);

    public void delMealBindInfoByBindId(@Param("mealBindId") String mealBindId);

    public List<Map<String ,Object>> queryBindComplateDataByMealId(@Param("mealId") String mealId);

}