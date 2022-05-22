package com.tdh.common;

import java.util.List;
import java.util.Map;

/**
 * 分页数据集合
 * @author 吴俊
 *
 * @param <T>
 */
public class PageList<T> {
	//当前页数据
	private List<T> dataList;
	//记录总数
	private long count;
	//当前页
	private int curPage;
	//每页显示的行数
	private int pageSize;
	
	private Map<String,Object> mapData;
	
	private int countPage;
	
	private int prevPage;
	
	private int nextPage;
	
	private Integer[] psList= {15,20,50,100,500,1000,3000,5000,10000};

	public int getPrevPage() {
		if(curPage>1){
			return curPage-1;
		}
		else{
			return 0;
		}
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		if(getCountPage()>curPage){
			return curPage+1;
		}
		else{
			return 0;
		}
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getCountPage() {
		if(count==0){
			return 1;
		}
		int n = (int) (count/pageSize);
		if(count%pageSize>0){
			n = n +1;
		}
		return n;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}


	public Integer[] getPsList() {
		return psList;
	}

	public void setPsList(Integer[] psList) {
		this.psList = psList;
	}

	/**
	 * 获取当前页的数据集合
	 * @return
	 */
	public List<T> getDataList() {
		return dataList;
	}
	/**
	 * 设置当前页的数据集合
	 * @param dataList 数据集合
	 */
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	/**
	 * 
	 * @param query
	 * @return
	 */
	public static PageList<Map<String, Object>> getInstance(QueryBean<Object> query, long count){
		PageList<Map<String, Object>> pageList=new PageList<Map<String, Object>>();
		pageList.setCurPage(query.getCurPage());
		pageList.setPageSize(query.getPageSize());
		pageList.setCount(count);
		return pageList;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public static PageList<Map<String, Object>> getInstance(QueryBean<Object> query, long count,Map<String,Object> mapData){
		PageList<Map<String, Object>> pageList=new PageList<Map<String, Object>>();
		pageList.setCurPage(query.getCurPage());
		pageList.setPageSize(query.getPageSize());
		pageList.setCount(count);
		pageList.setMapData(mapData);
		return pageList;
	}
	
	/**
	 * 获取总数据行数
	 * @return
	 */
	public long getCount() {
		return count;
	}
	/**
	 * 设置总数据行数
	 * @param count
	 */
	public void setCount(long count) {
		this.count = count;
	}
	/**
	 * 获取当前页
	 * @return
	 */
	public int getCurPage() {
		return curPage;
	}
	/**
	 * 设置当前页数
	 * @param curPage
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	/**
	 * 获取每页显示的行数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 设置当前页显示多少行
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Map<String, Object> getMapData() {
		return mapData;
	}
	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}
	
}
