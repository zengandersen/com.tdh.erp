package com.tdh.mapper;



import com.tdh.common.BaseMapper;
import com.tdh.pojo.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    public Map<String ,Object> queryCount(@Param("hospital_id") String hospital_id);



    public void delete(@Param("dict_id") String dict_id);


    public List<Map<String ,Object>> queryDictDetailByDeptTypeCode(@Param("hospital_id") String hospital_id,
                                                                   @Param("status") int status,
                                                                   @Param("dict_type_code_arr") String[] dict_type_code_arr);

 }