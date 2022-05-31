package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Output;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.jasper.tagplugins.jstl.core.Out;

import java.util.Map;


@Mapper
public interface OutputMapper extends BaseMapper<Output> {

}