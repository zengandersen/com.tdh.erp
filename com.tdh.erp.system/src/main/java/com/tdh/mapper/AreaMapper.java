package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AreaMapper extends BaseMapper<Area> {



    public void deleteAreaInfoById(@Param("area_id") String area_id);

    public Map<String ,Object> queryCount(@Param("hospital_id") String hospital_id);

    public List<Map<String ,Object>> queryAreaEmunInfo(@Param("hospital_id") String hospital_id, @Param("status") String status);

    public Area queueFindAreaId(@Param("area_id") String area_id);
}