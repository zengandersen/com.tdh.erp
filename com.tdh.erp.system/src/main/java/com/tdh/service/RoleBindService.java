package com.tdh.service;


import com.tdh.common.BaseService;

import com.tdh.common.CommonUtil;
import com.tdh.mapper.HospitalMapper;
import com.tdh.mapper.RoleBindMapper;

import com.tdh.pojo.MenuTableList;
import com.tdh.pojo.RoleBind;

import com.tdh.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("roleBindService")
public class RoleBindService extends BaseService<RoleBind, RoleBindMapper> {

    @Resource
    private RoleBindMapper roleBindMapper;

    /**
     * 根据权限id获取已绑定数据
     * @param role_id
     * @return
     * @throws Exception
     */
    public List<RoleBind> findBindInfoByIdServ (String role_id) throws Exception{
        return roleBindMapper.findBindInfoById(role_id);
    }

    /**
     * 处理菜单信息
     * @param Map
     * @param list
     * @return
     * @throws Exception
     */
    public Map<String ,Object> handleBindMenuData(  Map<String ,Object> Map,List<RoleBind> list) throws Exception{
        Map<String ,Object> result = new HashMap<>();
        List<MenuTableList> menuList  = new ArrayList<>();
        menuList = (List<MenuTableList>) Map.get("data");
        if(!CollectionUtils.isEmpty(list)){
            for(RoleBind a : list){
                for(MenuTableList b : menuList){
                    if(a.getMenu_id().equals(b.getId())){
                        b.setLAY_CHECKED(true);
                    }
                }
            }
        }
        result.put("code",0);
        result.put("msg","ok");
        result.put("data",menuList);
        result.put("count",menuList.size());
        return result;
    }

    /**
     * 处理绑定数据
     * @param role_id
     * @param ids
     * @param login_user
     */
    public void handleBind (String role_id,String ids, User login_user){
        deleteServ(role_id);
        insertServ(role_id,ids,login_user);
     }

    /**
     * 删除数据
     * @param role_id
     */
    public void deleteServ(String role_id){
        roleBindMapper.deleteBindInfoByRoleId(role_id);
     }

    /**
     * 新增数据
     * @param role_id
     * @param ids
     * @param login_user
     */
     public void insertServ(String role_id,String ids, User login_user){
         String [] arr = ids.split(",");
         for(int i=0;i <arr.length;i++) {
             RoleBind r = new RoleBind();
             r.setId(CommonUtil.createUUIDNoFlag());
             r.setRole_id(role_id);
             r.setMenu_id(arr[i]);
             r.setCreate_user(login_user.getUser_code());
             roleBindMapper.insert(r);
         }
     }
}
