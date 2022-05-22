package com.tdh.common;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wwq
 *
 * @param <T> 实体
 * @param <TMapper> 实体映射类
 */
public class BaseService<T, TMapper extends BaseMapper<T>> {
	
	private TMapper mapper;

	
	/**
	 * 查询结果List<Map<String,Object>>
	 * @param t
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	protected PageList<Map<String,Object>> selectMapPageSize(T t, int curPage, int pageSize){
		Map<String, Object> params= this.bindQueryPageSize(t, curPage, pageSize);
		//组织数据集合
		PageList<Map<String,Object>> pageList=new PageList<Map<String,Object>>();
		pageList.setCurPage(curPage);
		pageList.setPageSize(pageSize);
		//统计数据
		long count=mapper.countMap(this.bindQuery(t));
		pageList.setCount(count);
		//如果统计大于零则表示有数据，固设置在查询数据
		if(count>0){
			List<Map<String,Object>> dataList=mapper.selectMap(params);
			pageList.setDataList(dataList);
		}
		return pageList;
	}
	
	/**
	 * 绑定不分页查询条件
	 * @param t 查询对象
	 * @return
	 */
	public Map<String, Object> bindQuery(T t){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("p", t);
		return params;
	}
	
	/**
	 * 组织查询参数，主要用于列表分页类数据
	 * @param t 查询对象
	 * @param curPage 当前页
	 * @param pageSize 每页显示多收条
	 * @return 返回组织好的Map参数
	 */
	public Map<String, Object> bindQueryPageSize(T t, int curPage, int pageSize){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("p", t);
		params.put(Global.PAGINATION_PAGE_INDEX, curPage);
		params.put(Global.PAGINATION_PAGE_SIZE, pageSize);
		
		return params;
	}
	
	@Autowired
	public void setMapper(TMapper mapper) {
		this.mapper = mapper;
	}


	/**
	 * 获取字典枚举信息
	 * @param hospital_id
	 * @param dict_code
	 * @param arr
	 * @return
	 */
	public Map<String ,Object> queryDictEnumInfo(String hospital_id,String dict_code ,String []arr){
		Map<String ,Object> result = mapper.queryDictEnumInfo(hospital_id,dict_code,arr);
		return result;
	}

}
