package com.tdh.mapper;


import com.tdh.common.BaseMapper;

import com.tdh.pojo.RoleBind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleBindMapper extends BaseMapper<RoleBind> {


    public List<RoleBind> findBindInfoById(@Param("role_id") String role_id);

    public void deleteBindInfoByRoleId(@Param("role_id") String role_id);



}