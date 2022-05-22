package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.Config;
import com.tdh.common.PageList;
import com.tdh.mapper.AreaMapper;
import com.tdh.mapper.DeptMapper;
import com.tdh.pojo.Area;
import com.tdh.pojo.Dept;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("deptService")
public class DeptService extends BaseService<Dept, DeptMapper> {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private AreaService areaService;


    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Dept params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 科室管理新增及修改
     * @param list
     * @param login_user
     * @param count
     */
    public void mergeData(List<Dept> list,User login_user,int count) throws Exception{
        for(Dept dept : list){
            if(StringUtils.isEmpty(dept.getDept_id())){
                dept.setHospital_id(login_user.getHospital_id());
                dept.setDept_id(CommonUtil.createUUIDNoFlag());
                dept.setOrder_num(++count);
            }
            if(StringUtils.isNotEmpty(dept.getArea_id())){
                Area area =areaService.queueFindAreaId(dept.getArea_id());
                dept.setCompound_id(area.getCompound_id());
            }
            dept.setCreate_user(login_user.getUser_code());
            dept.setUpdate_user(login_user.getUser_code());
            deptMapper.mergeData(dept);
        }
    }

    /**
     * 删除
     * @param dept_id
     */
    public void deleteServ(String dept_id)  throws  Exception{
        deptMapper.deleteDeptInfoById(dept_id);
    }

    /**
     * 获取行数信息
     * @param hospital_id
     * @return
     * @throws Exception
     */
    public int queryCountServ(String hospital_id)throws Exception{
        Map<String ,Object> map = deptMapper.queryCount(hospital_id);
        int result = Integer.parseInt(String.valueOf(map.get("count")));
        return result;
    }

    /**
     * 获取科室信息枚举
     * @param hospital_id
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryDeptEmunInfoServ(String hospital_id) throws Exception{
        List<Map<String ,Object>> result = deptMapper.queryDeptEmunInfo(hospital_id,Config.statusClass.open);
        return result;
    }
}
