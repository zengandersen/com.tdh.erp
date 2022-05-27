package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Input;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface InputMapper extends BaseMapper<Input> {

    public Map<String ,Object> queryInputDetailInfoById(@Param("appId") String appId);



}