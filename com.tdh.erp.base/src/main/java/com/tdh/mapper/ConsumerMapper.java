package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Consumer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ConsumerMapper extends BaseMapper<Consumer> {


    public Map<String ,Object> queryConsumerDetailById(@Param("consumerId")String consumerId);

    public List<Map<String ,Object>>queryConsumerEnumAll();

    public void updateConsumerInfoById(Consumer consumer);

    public void deleteConsumerInfoById(@Param("consumerId") String consumerId);
}