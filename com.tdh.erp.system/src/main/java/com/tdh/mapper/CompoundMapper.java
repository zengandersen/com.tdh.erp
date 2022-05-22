package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Compound;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CompoundMapper extends BaseMapper<Compound> {



    public void deleteCompoundInfoById(@Param("compound_id") String compound_id);

    public Map<String ,Object> queryCount(@Param("hospital_id") String hospital_id);

    public List<Map<String ,Object>> queryCompoundEmunInfo(@Param("hospital_id") String hospital_id, @Param("status") String status);


}