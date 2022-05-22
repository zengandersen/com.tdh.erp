package com.tdh.service;


import com.tdh.common.BaseService;
import com.tdh.common.CommonUtil;
import com.tdh.common.Config;
import com.tdh.common.PageList;
import com.tdh.mapper.DictMapper;
import com.tdh.pojo.Dict;
import com.tdh.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("dictService")
public class DictService extends BaseService<Dict, DictMapper> {

    @Resource
    private DictMapper dictMapper;


    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageList<Map<String, Object>> selectPageMap(Dict params) throws  Exception{
        return this.selectMapPageSize(params, params.getCurPage(), params.getPageSize());
    }


    /**
     * 更新新增融合处理
     * @param list
     * @param login_user
     * @param count
     */
    public void mergeData(List<Dict> list,User login_user,int count){
        for(Dict dict : list){
            if(StringUtils.isEmpty(dict.getDict_id())){
                dict.setHospital_id(login_user.getHospital_id());
                dict.setDict_id(CommonUtil.createUUIDNoFlag());
                dict.setOrder_num(++count);
            }
            dict.setCreate_by(login_user.getUser_code());
            dict.setUpdate_by(login_user.getUser_code());
            dictMapper.mergeData(dict);
        }
    }



    /**
     * 获取数据行数
     * @param hospital_id
     * @return
     */
    public int queryCountServ(String hospital_id){
        Map<String ,Object> map = dictMapper.queryCount(hospital_id);
        int index= Integer.parseInt(String.valueOf(map.get("count")));
        return index;
    }


    /**
     * 通过字典id删除数据
     * @param dict_id
     */
    public void deleteServ(String dict_id){
        dictMapper.delete(dict_id);
    }

    /**
     * 获取字典明细
     * @param hospital_id
     * @param dict_type_code
     * @return
     */
    public List<Map<String ,Object>> queryDictDetail(String hospital_id,String  dict_type_code){
        List<Map<String ,Object>> queryInfo = null;
        List<Map<String ,Object>> result = new ArrayList<>();
        if(StringUtils.isNotEmpty(dict_type_code)){
            String [] arr = dict_type_code.split(",");
            queryInfo = dictMapper.queryDictDetailByDeptTypeCode(hospital_id,Config.statusClass.interget_open,arr);
             if(!CollectionUtils.isEmpty(queryInfo)){
                 for(int i=0;i<arr.length;i++){
                     String key = arr[i];
                     Map<String ,Object> resMap = new HashMap<>();
                     List<Map<String ,Object>> list = new ArrayList<>();
                     for(Map<String ,Object> map : queryInfo){
                         if(key.equals(String.valueOf(map.get("type")))){
                             Map<String ,Object> enumMap = new HashMap<>();
                             enumMap.put("name",map.get("name"));
                             enumMap.put("id",map.get("id"));
                             list.add(enumMap);
                         }
                     }
                     resMap.put(key,list);
                     result.add(resMap);
                 }

             }
        }
        return result;
    }
    /**
     * 通过字典获取驱动枚举值
     * @param hospital_id
     * @param dict_type_code
     * @param arr
     * @return
     * @throws Exception
     */
    public Map<String ,Object> handleEnumInfo(String hospital_id,String dict_type_code,String []arr)throws Exception{
        Map<String ,Object> result = this.queryDictEnumInfo(hospital_id,dict_type_code,arr);

        return result;
    }
}
