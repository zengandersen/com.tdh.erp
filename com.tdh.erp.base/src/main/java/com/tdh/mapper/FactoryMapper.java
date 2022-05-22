package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Factory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FactoryMapper extends BaseMapper<Factory> {

    public Map<String ,Object> queryCount();

    public Map<String ,Object> queryFactoryDetailById(@Param("factoryId") String factoryId);

    public void updateFactoryDetailById (Factory factory);

    public void deleteFactoryInfoById(@Param("factoryId") String factoryId);

    public List<Map<String ,Object>> queryFactoryInfoEnum();
}