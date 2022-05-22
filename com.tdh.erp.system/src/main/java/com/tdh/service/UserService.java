package com.tdh.service;


import com.tdh.common.*;
import com.tdh.mapper.UserMapper;
import com.tdh.pojo.Hospital;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.security.provider.MD2;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("sysUserService")
public class UserService extends BaseService<User,UserMapper> {

    @Resource
    private UserMapper userMapper;





    /**
     * 通过用户编码及用户密码获取用户对象信息
     * @param userCode
     * @param userPwd
     * @return
     */
    public User queryUserObjectByUserCode(String userCode, String userPwd){
        User sysUser = userMapper.queryUserObjectByUserCode(userCode,userPwd);
        return sysUser;
    }




    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(User params){
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 根据主键ID查询
     * @param id
     * @return
     */
    public User findById(String id) {
        return userMapper.findById(id);
    }




    /**
     * 根据用户查询
     * @param
     * @return
     */
    public User findByUser(String user_code) {
        return userMapper.findByUser(user_code.toUpperCase());
    }

    /**
     * 医院管理更新及新增
     * @param list
     * @param login_user
     * @throws Exception
     */
    public void mergeData(List<User> list, User login_user) throws  Exception{
        for(User user  : list){
            if(StringUtils.isEmpty(user.getUser_id())){
                user.setHospital_id(login_user.getHospital_id());
                user.setUser_id(CommonUtil.createUUIDNoFlag());
                user.setUser_pwd(Md5API.passwordCreate("a"));
            }
            if(StringUtils.isNotEmpty(user.getUser_pwd())){
                if(!Config.passwordConfig.CIPHERTEXT.equals(user.getInput_password())){
                    user.setUser_pwd(Md5API.passwordCreate(user.getInput_password()));
                }
            }
            user.setCreate_by(login_user.getUser_code());
            user.setUpdate_by(login_user.getUser_code());
            user.setJob_number("");
            userMapper.mergeData(user);
        }
    }

    public void deleteDeptInfoByIdServ(String user_id) throws  Exception{
         userMapper.deleteDeptInfoById(user_id);
    }

    /**
     * 铭文文加密
     * @param pageList
     * @return
     */
    public  PageList<Map<String, Object>> handlePasword(PageList<Map<String,Object>> pageList){
        if(!CollectionUtils.isEmpty(pageList.getDataList())){
            for(Map<String ,Object> map :pageList.getDataList()){
                map.put("input_password", Config.passwordConfig.CIPHERTEXT);
            }
        }
        return pageList;
    }



}
