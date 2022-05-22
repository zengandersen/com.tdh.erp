package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.Config;
import com.tdh.common.PageList;
import com.tdh.mapper.AreaMapper;
import com.tdh.mapper.RoleMapper;
import com.tdh.pojo.Area;
import com.tdh.pojo.Role;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("roleService")
public class RoleService extends BaseService<Role, RoleMapper> {

    @Resource
    private RoleMapper roleMapper;


    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Role params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 权限管理新增及修改
     * @param list
     * @param login_user
     * @param count
     */
    public void mergeData(List<Role> list,User login_user,int count) throws Exception{
        for(Role role : list){
            if(StringUtils.isEmpty(role.getRole_id())){
                role.setHospital_id(login_user.getHospital_id());
                role.setRole_id(CommonUtil.createUUIDNoFlag());
                role.setCompound_id(login_user.getCompound_id());
                role.setOrder_num(++count);
            }
            role.setCreate_user(login_user.getUser_code());
            role.setUpdate_user(login_user.getUser_code());
            roleMapper.mergeData(role);
        }
    }

    /**
     * 删除
     * @param role_id
     */
    public void deleteServ(String role_id)  throws  Exception{
        roleMapper.deleteRoleInfoById(role_id);
    }

    /**
     * 获取行数信息
     * @param hospital_id
     * @return
     * @throws Exception
     */
    public int queryCountServ(String hospital_id)throws Exception{
        Map<String ,Object> map = roleMapper.queryCount(hospital_id);
        int result = Integer.parseInt(String.valueOf(map.get("count")));
        return result;
    }

    /**
     * 获取权限枚举信息
     * @param hospital_id
     * @param compound_id
     * @return
     */
    public List<Map<String ,Object>> queryRoleEnumByIdServ(String hospital_id ,String compound_id){
        List<Map<String ,Object>> result = roleMapper.queryRoleEnumById(hospital_id,compound_id);
        return result;
    }


}
