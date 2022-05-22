package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.Config;
import com.tdh.common.PageList;
import com.tdh.mapper.AreaMapper;
import com.tdh.pojo.Area;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("areaService")
public class AreaService extends BaseService<Area, AreaMapper> {

    @Resource
    private AreaMapper areaMapper;


    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Area params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 诊区管理新增及修改
     * @param list
     * @param login_user
     * @param count
     */
    public void mergeData(List<Area> list,User login_user,int count) throws Exception{
        for(Area area : list){
            if(StringUtils.isEmpty(area.getArea_id())){
                area.setHospital_id(login_user.getHospital_id());
                area.setArea_id(CommonUtil.createUUIDNoFlag());
                area.setOrder_num(++count);
            }
            area.setCreate_user(login_user.getUser_code());
            area.setUpdate_user(login_user.getUser_code());
            areaMapper.mergeData(area);
        }
    }

    /**
     * 删除
     * @param area_id
     */
    public void deleteServ(String area_id)  throws  Exception{
        areaMapper.deleteAreaInfoById(area_id);
    }

    /**
     * 获取行数信息
     * @param hospital_id
     * @return
     * @throws Exception
     */
    public int queryCountServ(String hospital_id)throws Exception{
        Map<String ,Object> map = areaMapper.queryCount(hospital_id);
        int result = Integer.parseInt(String.valueOf(map.get("count")));
        return result;
    }

    /**
     * 获取诊区信息枚举
     * @param hospital_id
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryAreaEmunInfoServ(String hospital_id) throws Exception{
        List<Map<String ,Object>> result = areaMapper.queryAreaEmunInfo(hospital_id,Config.statusClass.open);
        return result;
    }


    /**
     * 根据诊区id进行查询 并反对诊区对象
     * @param area_id
     */
    public Area queueFindAreaId(String area_id)  throws  Exception{
          Area Area  = areaMapper.queueFindAreaId(area_id);
          return Area;
    }

}
