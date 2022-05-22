package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Role;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    public void  deleteRoleInfoById(@Param("role_id") String role_id);

    public List<Map<String,Object>> queryRoleEnumById(@Param("hospital_id") String hospital_id,
                                                      @Param("compound_id") String compound_id);




}
