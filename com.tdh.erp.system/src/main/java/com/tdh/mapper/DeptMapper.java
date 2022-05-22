package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {



    public void deleteDeptInfoById(@Param("dept_id") String area_id);

    public Map<String ,Object> queryCount(@Param("hospital_id") String hospital_id);

    public List<Map<String ,Object>> queryDeptEmunInfo(@Param("hospital_id") String hospital_id, @Param("status") String status);


}