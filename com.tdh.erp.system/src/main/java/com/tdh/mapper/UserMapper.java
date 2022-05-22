package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    public void insert(User params);
    public List<Map<String, Object>> queryUserInfo(@Param("user_id") String user_id);
    public User queryUserObjectByUserCode(@Param("user_code") String user_code, @Param("user_pwd") String user_pwd);
    public User findByUser(String user_code);
    public String findByUserMenu(String user_id);
    public void deleteDeptInfoById(@Param("user_id") String user_id);
    public List<Map<String ,Object>> queryUserLikeAdmin(@Param("hospital_id") String hospital_id,
                                                        @Param("compound_id") String compound_id);
    public Map<String ,Object> queryLoginUserOfRoleInfoById(@Param("user_code") String user_code);
}
