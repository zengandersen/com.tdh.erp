package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Hospital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HospitalMapper extends BaseMapper<Hospital> {

    public void deleteHospitalInfoById(@Param("hospital_id") String hospital_id);

    public List<Map<String ,Object>> queryHospitalEmunInfo(@Param("hospital_id") String hospital_id, @Param("status") String status);


}