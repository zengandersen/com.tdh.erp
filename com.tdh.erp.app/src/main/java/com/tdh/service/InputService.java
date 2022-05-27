package com.tdh.service;


import com.tdh.common.*;
import com.tdh.mapper.InputMapper;
import com.tdh.pojo.Input;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("inputService")
public class InputService extends BaseService<Input, InputMapper> {

    @Resource
    private InputMapper inputMapper;

    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Input params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }


    /** 新增数据
     * @param user
     * @param input
     */
    public void insertServ(User user, Input input) throws Exception {
        input.setAppId(CommonUtil.createUUIDNoFlag());
        input.setCreateUser(user.getUser_code());
        inputMapper.insert(input);
    }

    /**
     * 获取明细数据
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String ,Object> queryInputDetailInfoByIdServ(String appId) throws Exception{
        Map<String ,Object> result = inputMapper.queryInputDetailInfoById(appId);
        return result;
    }
}
