package com.tdh.service;


import com.tdh.common.*;
import com.tdh.mapper.ConsumerMapper;
import com.tdh.pojo.Consumer;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("consumerService")
public class ConsumerService extends BaseService<Consumer, ConsumerMapper> {

    @Resource
    private ConsumerMapper consumerMapper;

    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Consumer params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 通过id 获取客户明细数据
     * @param consumerId
     * @return
     */
    public Map<String ,Object> queryConsumerDetailByIdServ(String consumerId) throws  Exception{
        Map<String ,Object> map = consumerMapper.queryConsumerDetailById(consumerId);
        map = handleResStatus(map);
        if(CollectionUtils.isEmpty(map)){
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * 获取客户信息下拉列表数据
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryConsumerEnumAll() throws  Exception{
        List<Map<String ,Object>> list = consumerMapper.queryConsumerEnumAll();
        return list;
    }

    /**
     * 新增数据
     * @param user
     * @param consumer
     * @throws Exception
     */
    public void insertServ(User user,Consumer consumer) throws Exception{
        consumer.setConsumerId(CommonUtil.createUUIDNoFlag());
        consumer.setCreateUser(user.getUser_code());
        List<Map<String,String>> table = AddrUtils.addressResolution(consumer.getAddr());
        consumer.setProvince(table.get(0).get("province"));
        consumerMapper.insert(this.handleRequestStatus(consumer));
    }

    /**
     * 更新数据
     * @param user
     * @param consumer
     * @throws Exception
     */
    public void updateServ(User user,Consumer consumer) throws Exception {
        consumer.setUpdateUser(user.getUser_code());
        List<Map<String,String>> table = AddrUtils.addressResolution(consumer.getAddr());
        consumer.setProvince(table.get(0).get("province"));
        consumerMapper.updateConsumerInfoById(this.handleRequestStatus(consumer));
    }

    /**
     * 删除数据
     * @param consumerId
     * @throws Exception
     */
    public void delServ(String consumerId)throws Exception {
        consumerMapper.deleteConsumerInfoById(consumerId);
    }


    public Consumer handleRequestStatus(Consumer consumer) throws Exception{
        if(StringUtils.isNotEmpty(consumer.getReturn())){
            consumer.setIsReturn(Config.checkBoxStatus.open);
        }else{
            consumer.setIsReturn(Config.checkBoxStatus.close);
        }
        if(StringUtils.isNotEmpty(consumer.getFriend())){
            consumer.setIsFriend(Config.checkBoxStatus.open);
        }else{
            consumer.setIsFriend(Config.checkBoxStatus.close);
        }
        if(StringUtils.isNotEmpty(consumer.getBlack())){
            consumer.setIsBlack(Config.checkBoxStatus.open);
        }else{
            consumer.setIsBlack(Config.checkBoxStatus.close);
        }
        return consumer;
    }

    public Map<String ,Object> handleResStatus(Map<String ,Object> map) throws Exception{
        if(Config.checkBoxStatus.open == Integer.parseInt(String.valueOf(map.get("is_return")))){
            map.put("return",Config.checkBoxStatus.on);
        }
        if(Config.checkBoxStatus.open == Integer.parseInt(String.valueOf(map.get("is_black")))){
            map.put("black",Config.checkBoxStatus.on);
        }
        if(Config.checkBoxStatus.open == Integer.parseInt(String.valueOf(map.get("is_friend")))){
            map.put("friend",Config.checkBoxStatus.on);
        }
        return map;
    }
}
