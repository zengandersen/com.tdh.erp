package com.tdh.service;


import com.tdh.common.*;
import com.tdh.mapper.GoodsMapper;
import com.tdh.pojo.Goods;
import com.tdh.pojo.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Clob;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("goodsService")
public class GoodsService extends BaseService<Goods, GoodsMapper> {

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Goods params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }


    /**
     * 编码及名称重复校验
     * @param goodsName
     * @param goodsCode
     * @return
     * @throws Exception
     */
    public String verifyAddRepeatParam(String goodsName,String goodsCode) throws Exception{
        Map<String ,Object> map = goodsMapper.verifyRepeatParam(goodsName,goodsCode);
        int result = Integer.parseInt(String.valueOf(map.get("count")));
        if(Config.integerClass.zero < result){
            return ReturnUtils.ReturnParam.param_repeat;
        }
        return ReturnUtils.ReturnParam.success;
    }


    /**
     * 编码及名称重复校验
     * @param goodsName
     * @param goodsCode
     * @return
     * @throws Exception
     */
    public String verifyEditRepeatParam(String goodsName,String goodsCode) throws Exception{
        Map<String ,Object> map = goodsMapper.verifyRepeatParam(goodsName,goodsCode);
        int result = Integer.parseInt(String.valueOf(map.get("count")));
        if(Config.integerClass.one < result){
            return ReturnUtils.ReturnParam.param_repeat;
        }
        return ReturnUtils.ReturnParam.success;
    }

    /**
     * 插入数据
     * @param user
     * @param goods
     * @throws Exception
     */
    public void insertServ(User user,Goods goods) throws Exception{
        goods.setGoodsId(CommonUtil.createUUIDNoFlag());
        goods.setPinyin(PinyinAPI.getPinYinHeadChar(goods.getGoodsName()));
        goods.setEname(PinyinAPI.getEname(goods.getGoodsName()));
        goods.setCreateUser(user.getUser_code());
        goods.setGoodsImg(ImgUtils.convertImageToBase64(goods.getGoodsImg(),1000));
        goodsMapper.insert(goods);
    }

    /**
     * 更新数据
     * @param user
     * @param goods
     * @throws Exception
     */
    public void updateServ(User user,Goods goods) throws Exception{
        goods.setUpdateUser(user.getUser_code());
        goods.setPinyin(PinyinAPI.getPinYinHeadChar(goods.getGoodsName()));
        goods.setEname(PinyinAPI.getEname(goods.getGoodsName()));
        boolean flag = goods.getGoodsImg().contains("data:image/png;base64");
        if(StringUtils.isNotEmpty(goods.getGoodsImg())){
            if(flag == false){
                goods.setGoodsImg(ImgUtils.convertImageToBase64(goods.getGoodsImg(),1000));
            }
        }
        goodsMapper.updateGoodsById(goods);
    }

    /**
     * 获取明细数据
     * @param goodsId
     * @return
     * @throws Exception
     */
    public Map<String ,Object> queryGoodsDetailByIdServ(String goodsId) throws Exception{
        Map<String ,Object> result = goodsMapper.queryGoodsInfoById(goodsId);
        if(StringUtils.isNotEmpty(String.valueOf(result.get("goods_img")))){
            String strImg = ClobUtils.handleGoodsImageObject(result);
            result.put("goods_img",strImg);
        }
        if(CollectionUtils.isEmpty(result)){
            result = new HashMap<>();
        }
        return result;
    }


    public void delGoodsInfoByIdServ(String id) throws Exception{
        goodsMapper.delGoodsById(id);
    }

    public List<Map<String ,Object>> queryGoodsEnumByIdServ(String goodsId) throws Exception{
        List<Map<String ,Object>> result = goodsMapper.queryGoodsEnumById(goodsId);
        return result;
    }

    /**
     * 通过厂商获取对应的商品信息
     * @param status
     * @param factoryId
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryGoodsEnumByFactoryInfo(int status,String factoryId) throws Exception{
        List<Map<String ,Object>> result = goodsMapper.queryGoodsEnumByFactoryId(status,factoryId);
        if(CollectionUtils.isEmpty(result)){
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     * 通过厂商获取对应的商品信息
     * @param status
     * @param factoryId
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryGoodsEnumAndImgByFactoryIdServ(int status,String factoryId) throws Exception{
        List<Map<String ,Object>> result = goodsMapper.queryGoodsEnumAndImgByFactoryId(status,factoryId);
        if(CollectionUtils.isEmpty(result)){
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     *
     * @param pageList
     * @return
     * @throws Exception
     */
    public PageList<Map<String, Object>> handlePageListImageData(PageList<Map<String, Object>> pageList) throws Exception{
        List<Map<String ,Object>> list = pageList.getDataList();
        if(!CollectionUtils.isEmpty(list)){
            for(Map<String ,Object> map : list){
                map.put("goods_img",ClobUtils.handleGoodsImageObject(map));
                System.out.println();
            }
        }
        return pageList;
    }




    /**
     *
     * @param list
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> handleListImageData(List<Map<String, Object>> list) throws Exception{
        if(!CollectionUtils.isEmpty(list)){
            for(Map<String ,Object> map : list){
                map.put("goods_img",ClobUtils.handleGoodsImageObject(map));
                System.out.println();
            }
        }
        return list;
    }




    /**
     *
     * @param text
     * @param strToReplace
     * @param replaceWithThis
     * @return
     */
    public static String replaceLast(String text, String strToReplace, String replaceWithThis) {
        return text.replaceFirst("(?s)" + strToReplace + "(?!.*?" + strToReplace + ")", replaceWithThis);
    }

}
