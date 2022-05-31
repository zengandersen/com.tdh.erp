package com.tdh.service;


import com.alibaba.fastjson.JSON;
import com.tdh.common.*;
import com.tdh.mapper.*;
import com.tdh.pojo.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tdh.common.Config.sysTrackCode;


@Service("outputService")
public class OutputService extends BaseService<Output, OutputMapper> {

    @Resource
    private InputMapper inputMapper;

    @Resource
    private RepertoryMapper repertoryMapper;

    @Resource
    private MealBindMapper mealBindMapper;

    @Resource
    private MealMapper mealMapper;

    @Resource
    private LogMovingPinMapper logMovingPinMapper;

    @Resource
    private LogMovingMealMapper logMovingMealMapper;



    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Output params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }




}
