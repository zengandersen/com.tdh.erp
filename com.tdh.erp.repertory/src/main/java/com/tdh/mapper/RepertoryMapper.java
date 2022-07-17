package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Repertory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RepertoryMapper extends BaseMapper<Repertory> {

        public void updateRepertoryInfoById(Repertory repertory);

        public Map<String ,Object> queryRepertoryDetailById(@Param("repertoryId") String repertoryId);

        public void delRepertoryInfoById(@Param("repertoryId") String repertoryId);



        public List<Map<String ,Object>> queryRepertoryBindInfoById(@Param("repertoryIds") String [] repertoryIds);

        public List<Map<String,Object>> queryRepertoryInfoByGoodsId(@Param("goodsIds") String []goodsIds);

        public Map<String ,Object>  queryRepertoryInfoVeify(@Param("goodsId") String goodsId);
 }