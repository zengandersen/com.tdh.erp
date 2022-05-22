package com.tdh.common;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
	
	/**
	 * 插入数据
	 * @param t
	 */
	public void insert(T t);
	/**
	 * 完全根据对象的修改数据，即NULL也会修改
	 * @param t 必须体统主键ID
	 */
	public void update(T t);

	/**
	 * 根据条件查询固定段集合
	 * @param params 查询参数
	 * @param columns 要查询的列
	 */
	public List<Map<String, Object>> selectMap(Map<String, Object> params);
	
	
	/**
	 * 根据条件统计数据行数，一般与Select方法配合使用
	 * @param params 参数查询
	 * @return
	 */
	public long countMap(Map<String, Object> params);
	
	
	/**
	 * 根据主键ID查询对象
	 * @param id
	 * @return
	 */
	public T findById(String id);
	/**
	 * 根据主键ID删除数据
	 * @param id
	 */
	public void deleteById(String id);

	public void mergeData(T t);

	public Map<String, Object> queryCount(String hospital_id);

	
	public Map<String ,Object> queryDictDetail(String hospital_id,int status,String dict_type_code);


	public Map<String ,Object> queryDictEnumInfo(@Param("hospital_id") String hospital_id,
												 @Param("dict_code") String dict_code,
                                                 @Param("dict_type_code_arr") String[] dict_type_code_arr);
	
}
