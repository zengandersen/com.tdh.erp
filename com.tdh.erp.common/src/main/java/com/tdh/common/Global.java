package com.tdh.common;


/**
 * 分页
 * @author 吴俊
 *
 */
public class Global {

	/**
	 * 查询关键字：当前页
	 */
	public static String PAGINATION_PAGE_INDEX="mybatis.page.index";
	/**
	 * 查询关键字：每页显示多少条
	 */
	public static String PAGINATION_PAGE_SIZE="mybatis.page.size";
	
	public final static String PSLIST="15,20,50,100,500,1000";
	
	/**
	 * 默认每页显示50条数据
	 */
	public final static int PAGE_SIZE=50;
	
	/**
	 * 医院编码
	 */
	public final static String HOSPITAL_ID="ADH";
	
	public final static String MESSAGE_SEND_URL="https://adh.wang/wxpt/messageSendApption.html";
	
}
