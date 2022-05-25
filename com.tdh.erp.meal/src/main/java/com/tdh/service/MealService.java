package com.tdh.service;


import com.tdh.common.*;
import com.tdh.mapper.MealMapper;
import com.tdh.pojo.Meal;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
        meal.setMealImg(ImgUtils.convertImageToBase64(meal.getMealImg(),1000));
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
        if(StringUtils.isNotEmpty(String.valueOf(result.get("meal_img")))){
            String strImg = ClobUtils.handleMealmageObject(result);
            result.put("meal_img",strImg);
        }
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
        boolean flag = meal.getMealImg().contains("data:image/png;base64");
        if(StringUtils.isNotEmpty(meal.getMealImg())){
            if(flag == false){
                meal.setMealImg(ImgUtils.convertImageToBase64(meal.getMealImg(),1000));
            }
        }
        mealMapper.updateMealById(meal);
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
                map.put("meal_img",ClobUtils.handleMealmageObject(map));
                System.out.println();
            }
        }
        return pageList;
    }
}
