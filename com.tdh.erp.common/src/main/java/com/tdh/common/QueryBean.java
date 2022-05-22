package com.tdh.common;

import java.io.Serializable;

/**
 * 通用查询超类
 * @author 吴俊
 *
 */
public class QueryBean<T>   implements Cloneable,Serializable{
	
	/**
	 * 克隆方法
	 */
	@Override
	public T clone() throws CloneNotSupportedException {
		return (T) super.clone();
	}
	
	
	public String begin_datetime1;
	public String end_datetime1;
	
	public String begin_datetime2;
	public String end_datetime2;
	
	
	/*
	 * 当前页
	 */
	private int curPage;
	/*
	 * 每页显示多少行
	 */
	private int pageSize;
	/**
	 * 获取当前页
	 * @return
	 */
	public int getCurPage() {
		if(curPage==0){
			return 1;
		}
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	/**
	 * 获取每页显示多少条
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getBegin_datetime1() {
		return begin_datetime1;
	}

	public void setBegin_datetime1(String begin_datetime1) {
		this.begin_datetime1 = begin_datetime1;
	}

	public String getEnd_datetime1() {
		return end_datetime1;
	}

	public void setEnd_datetime1(String end_datetime1) {
		this.end_datetime1 = end_datetime1;
	}

	public String getBegin_datetime2() {
		return begin_datetime2;
	}

	public void setBegin_datetime2(String begin_datetime2) {
		this.begin_datetime2 = begin_datetime2;
	}

	public String getEnd_datetime2() {
		return end_datetime2;
	}

	public void setEnd_datetime2(String end_datetime2) {
		this.end_datetime2 = end_datetime2;
	}
	
	
}
