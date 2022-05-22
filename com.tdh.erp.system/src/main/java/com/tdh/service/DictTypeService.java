package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.PageList;
import com.tdh.mapper.DictTypeMapper;
import com.tdh.pojo.DictType;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("dictTypeService")
public class DictTypeService extends BaseService<DictType, DictTypeMapper> {

    @Resource
    private DictTypeMapper dictTypeMapper;


    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(DictType params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }

    /**
     * 新增
     * @param list
     */
    public void  mergeData( List<DictType> list ,User login_user,int count) throws  Exception{
        for(DictType dictType : list){
            if(StringUtils.isEmpty(dictType.getId())){
                //新增
                dictType.setHospital_id(login_user.getHospital_id());
                dictType.setId(CommonUtil.createUUIDNoFlag());
                dictType.setOrder_num(++count);
            }
            dictType.setCreate_by(login_user.getUser_code());
            dictType.setUpdate_by(login_user.getUser_code());
            dictTypeMapper.mergeData(dictType);
        }
    }



    /**
     * 删除数据
     * @param dict_type_code
     * @param hospital_id
     */
    public void deleteServ(String dict_type_code,String hospital_id) throws  Exception{
        dictTypeMapper.deleteDetail(dict_type_code,hospital_id);
        dictTypeMapper.delete(dict_type_code,hospital_id);
    }

    /**
     * 获取字典类型数据数量
     * @param hospital_id
     * @return
     */
    public int queryCountServ(String hospital_id) throws  Exception{
        Map<String ,Object> map = dictTypeMapper.queryCount(hospital_id);
        int count = Integer.parseInt(String.valueOf(map.get("count")));
        return count;
    }


}
