package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.Config;
import com.tdh.common.PageList;
import com.tdh.mapper.HospitalMapper;
import com.tdh.pojo.Hospital;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("hospitalService")
public class HospitalService extends BaseService<Hospital, HospitalMapper> {

    @Resource
    private HospitalMapper hospitalMapper;
    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Hospital params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }


    /**
     * 医院管理更新及新增
     * @param list
     * @param login_user
     * @throws Exception
     */
    public void mergeData(List<Hospital> list,User login_user) throws  Exception{
        for(Hospital hospital : list){
           hospital.setCreate_user(login_user.getUser_code());
           hospital.setUpdate_user(login_user.getUser_code());
           hospitalMapper.mergeData(hospital);
        }
    }

    /**
     * 根据id删除数据
     * @param hospital_id
     * @throws Exception
     */
    public void deleteHospitalInfoServ(String hospital_id) throws  Exception{
        hospitalMapper.deleteHospitalInfoById(hospital_id);
    }

    /**
     * 获取院区信息枚举
     * @param hospital_id
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryHospitalEmunInfoServ(String hospital_id) throws Exception{
        List<Map<String ,Object>> result = hospitalMapper.queryHospitalEmunInfo(hospital_id, Config.statusClass.open);
        return result;
    }
}
