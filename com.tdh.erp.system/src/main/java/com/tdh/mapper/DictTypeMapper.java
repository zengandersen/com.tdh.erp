package com.tdh.mapper;



import com.tdh.common.BaseMapper;
import com.tdh.pojo.DictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {

    public void delete(@Param("dict_type_code") String dict_type_code,
                       @Param("hospital_id") String hospital_id);


    public void deleteDetail(@Param("dict_type_code") String dict_type_code,
                             @Param("hospital_id") String hospital_id);
}