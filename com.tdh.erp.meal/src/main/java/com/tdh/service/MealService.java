package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.PageList;
import com.tdh.mapper.MealMapper;
import com.tdh.pojo.Meal;
import com.tdh.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service("mealService")
public class MealService extends BaseService<Meal, MealMapper> {

    @Resource
    private MealMapper mealMapper;

    /**
     * 分页查询数据
     *
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Meal params) throws Exception {
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * @param user
     * @param meal
     */
    public void insertServ(User user, Meal meal) throws Exception {
        meal.setMealId(CommonUtil.createUUIDNoFlag());
        meal.setCreateUser(user.getUser_code());
        mealMapper.insert(meal);
    }

    /**
     * 根据id 获取对应数据
     *
     * @param mealId
     * @return
     */
    public Map<String, Object> queryMealInfoByIdServ(String mealId) throws Exception {
        Map<String, Object> result = mealMapper.queryMealInfoById(mealId);
        if (CollectionUtils.isEmpty(result)) {
            result = new HashMap<>();
        }
        return result;
    }

    /**
     * 删除
     *
     * @param mealId
     */
    public void delInfoServ(String mealId) throws Exception {
        mealMapper.delMealById(mealId);
    }


    /**
     * 更新套餐
     *
     * @param user
     * @param meal
     * @throws Exception
     */
    public void updateInfoServ(User user, Meal meal) throws Exception {
        meal.setUpdateUser(user.getUser_code());
        mealMapper.updateMealById(meal);
    }
}
