package com.tdh.service;


import com.tdh.common.*;
import com.tdh.mapper.FactoryMapper;
import com.tdh.pojo.Factory;
import com.tdh.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("factoryService")
public class FactoryService extends BaseService<Factory, FactoryMapper> {

    @Resource
    private FactoryMapper factoryMapper;


    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Factory params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }


    /**
     *  插入数据
     * @param user
     * @param factory
     * @param orderNum
     * @throws Exception
     */
    public void insertServ(User user, Factory factory,int orderNum) throws Exception{
        factory.setFactoryId(CommonUtil.createUUIDNoFlag());
        factory.setOrderNum(orderNum);
        factory.setPinyin(PinyinAPI.getPinYinHeadChar(factory.getFactoryName()));
        factory.setEname(PinyinAPI.getEname(factory.getFactoryName()));
        factory.setCreateUser(user.getUser_code());
        factoryMapper.insert(factory);
    }

    /**
     * 获取排序数据
     * @return
     * @throws Exception
     */
    public int queryOrderNum () throws Exception{
        Map<String ,Object> map = factoryMapper.queryCount();
        int result = Integer.parseInt(String.valueOf(map.get("count")))+ Config.integerClass.one;
        return result;
    }

    /**
     * 根据id 获取明细数据
     * @param factoryId
     * @return
     * @throws Exception
     */
    public Map<String ,Object> queryFactoryDetailByIdServ(String factoryId) throws Exception{
        Map<String ,Object> factory = factoryMapper.queryFactoryDetailById(factoryId);
        return factory;
    }

    /**
     * 行数据修改
     * @param user
     * @param factory
     * @throws Exception
     */
    public void  margeDataServ(User user,Factory factory) throws  Exception {
        factory.setUpdateUser(user.getUser_code());
        factoryMapper.mergeData(factory);
    }


    /**
     * 更新数据
     * @param user
     * @param factory
     * @throws Exception
     */
    public void updateServ(User user,Factory factory) throws Exception{
        factory.setUpdateUser(user.getUser_code());
        factory.setPinyin(PinyinAPI.getPinYinHeadChar(factory.getFactoryName()));
        factory.setEname(PinyinAPI.getEname(factory.getFactoryName()));
        factoryMapper.updateFactoryDetailById(factory);
    }

    /**
     * 删除数据
     * @param factoryId
     * @throws Exception
     */
    public void delServ(String factoryId) throws Exception{
        factoryMapper.deleteFactoryInfoById(factoryId);
    }

    /**
     * 获取厂商枚举信息
     * @return
     */
    public List<Map<String ,Object>> queryFactoryInfoEnumServ(){
        List<Map<String ,Object>> list = factoryMapper.queryFactoryInfoEnum();
        if(CollectionUtils.isEmpty(list)){
            list = new ArrayList<>();
        }
        return list;
    }



}
