package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.MealBind;
import com.tdh.pojo.MealBindRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface MealBindResMapper extends BaseMapper<MealBindResMapper> {

    public List<MealBindRes> queryRepertoryInfoAll(@Param("searchName") String searchName);

}