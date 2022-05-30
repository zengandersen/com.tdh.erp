package com.tdh.mapper;


import com.tdh.common.BaseMapper;
import com.tdh.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    public int updateGoodsById (Goods goods);

    public int delGoodsById(@Param("goodsId") String goodsId);

    public Map<String ,Object> queryGoodsInfoById (@Param("goodsId") String goodsId);

    public List<Map<String ,Object>> queryGoodsInfoEnum(@Param("status") int status);

    public Map<String ,Object> verifyRepeatParam(@Param("goodsName") String goodsName,
                                                 @Param("goodsCode") String goodsCode);

    public List<Map<String ,Object>>queryGoodsEnumByFactoryId(@Param("status") int status,
                                                       @Param("factoryId") String factoryId);

    public List<Map<String ,Object>> queryGoodsEnumById(@Param("goodsId") String goodsId);

    public List<Map<String ,Object>> queryGoodsEnumAndImgByFactoryId(@Param("status") int status,
                                                                     @Param("factoryId") String factoryId);


}