package com.tdh.service;


import com.tdh.common.*;
import com.tdh.mapper.GoodsMapper;
import com.tdh.mapper.RepertoryMapper;
import com.tdh.pojo.Repertory;
import com.tdh.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service("repertoryService")
public class RepertoryService extends BaseService<Repertory, RepertoryMapper> {

   @Resource
    private RepertoryMapper repertoryMapper;

   @Resource
   private GoodsMapper goodsMapper;

    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Repertory params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    public void insertServ(User user,Repertory repertory){
        repertory.setRepertoryId(CommonUtil.createUUIDNoFlag());
        Map<String ,Object> goodsMap = this.queryGoodsDetailInfoById(repertory.getGoodsId());
        if(!CollectionUtils.isEmpty(goodsMap)){
            repertory.setGoodsName(String.valueOf(goodsMap.get("goods_name")));
        }
        repertory.setCreateUser(user.getUser_code());
        repertoryMapper.insert(repertory);
    }

    /**
     * 根据商品id获取商品明细
     * @param goodsId
     * @return
     */
    public Map<String ,Object> queryGoodsDetailInfoById(String goodsId){
        Map<String ,Object> result = goodsMapper.queryGoodsInfoById(goodsId);
        return result;
    }

    /**
     * 获取仓库明细数据
     * @param repertoryId
     * @return
     */
    public Map<String ,Object> queryRepertoryDetailByIdServ(String repertoryId){
        Map<String ,Object> result = repertoryMapper.queryRepertoryDetailById(repertoryId);
        if(CollectionUtils.isEmpty(result)){
            result = new HashMap<>();
        }
        return result;
    }

    /**
     * 更新库存信息
     * @param user
     * @param repertory
     * @throws Exception
     */
    public void updateServ(User user,Repertory repertory) throws Exception{
        repertory.setUpdateUser(user.getUser_code());
        repertoryMapper.updateRepertoryInfoById(repertory);
    }


    public void delServ(String repertoryId) throws Exception{
        repertoryMapper.delRepertoryInfoById(repertoryId);
    }
}
