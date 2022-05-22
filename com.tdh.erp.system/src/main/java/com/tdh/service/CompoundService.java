package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.Config;
import com.tdh.common.PageList;
import com.tdh.mapper.CompoundMapper;
import com.tdh.pojo.Compound;
import com.tdh.pojo.Compound;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("CompoundService")
public class CompoundService extends BaseService<Compound, CompoundMapper> {

    @Resource
    private CompoundMapper CompoundMapper;
    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Compound params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }


    /**
     * 分院管理新增及修改
     * @param list
     * @param login_user
     * @param count
     */
    public void mergeData(List<Compound> list, User login_user, int count) throws Exception{
        for(Compound compound : list){
            if(StringUtils.isEmpty(compound.getCompound_id())){
                compound.setHospital_id(login_user.getHospital_id());
                compound.setCompound_id(CommonUtil.createUUIDNoFlag());
                compound.setOrder_num(++count);
            }
            compound.setCreate_user(login_user.getUser_code());
            compound.setUpdate_user(login_user.getUser_code());
            CompoundMapper.mergeData(compound);
        }
    }

    /**
     * 根据id删除数据
     * @param Compound_id
     * @throws Exception
     */
    public void deleteCompoundInfoServ(String Compound_id) throws  Exception{
        CompoundMapper.deleteCompoundInfoById(Compound_id);
    }

    /**
     * 获取分院区信息枚举
     * @param compound_id
     * @return
     * @throws Exception
     */
    public List<Map<String ,Object>> queryCompoundEmunInfoServ(String compound_id) throws Exception{
        List<Map<String ,Object>> result = CompoundMapper.queryCompoundEmunInfo(compound_id, Config.statusClass.open);
        return result;
    }


    /**
     * 获取行数信息
     * @param hospital_id
     * @return
     * @throws Exception
     */
    public int queryCountServ(String hospital_id)throws Exception{
        Map<String ,Object> map = CompoundMapper.queryCount(hospital_id);
        int result = Integer.parseInt(String.valueOf(map.get("count")));
        return result;
    }

}
