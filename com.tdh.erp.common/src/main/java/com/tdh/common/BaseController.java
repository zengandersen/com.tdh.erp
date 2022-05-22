package com.tdh.common;

import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BaseController{
	protected HttpServletRequest request;
	protected HttpSession session;
	@Autowired
	public final static Logger log= Logger.getLogger(BaseController.class);






	public void initQuery(HttpServletRequest request, QueryBean obj) throws Exception {
		if (obj.getCurPage() < 1) {
			obj.setCurPage(1);
		}
		if (obj.getPageSize() == 0) {
			obj.setPageSize(Global.PAGE_SIZE);
		}
	}
	
	public void initQueryPageSize(HttpServletRequest request, QueryBean obj) throws Exception {
		if (obj.getCurPage() < 1) {
			obj.setCurPage(1);
		}
		if (obj.getPageSize() == 0) {
			obj.setPageSize(5);
		}
	}
//
//	public User getLoginInfo(HttpServletRequest request) throws Exception{
//		Object obj=request.getSession().getAttribute(Config.LOGIN_INFO);
//		User user=new User();
//		if(obj!=null){
//			user=(User)obj;
//
//		}
//		return user;
//
//	}
	/**
	 * 获取系统全路径
	 * @param request
	 * @return
	 * @throws LoginException
	 */
	public String getRootPath(HttpServletRequest request) throws Exception{
		String rootPath = "";
		String rl = request.getRequestURL().toString();
		String[] rls = rl.split("://");
		String cp = request.getContextPath();
		if (rls[1].indexOf('/') >= 0) {
			rootPath = rls[0] + "://"
					+ rls[1].substring(0, rls[1].indexOf('/'));
		} else {
			rootPath = rls[0] + "://" + rls[1];
		}
		if (cp != null) {
			rootPath = rootPath + cp;
		}
		return rootPath;
	}
	
	/**
	 * 日期解析器
	 * @param request
	 * @param binder
	 */
	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat2, true));
		
		String spath=request.getServletPath();
		if(UtilAPI.isNull(spath)){
			return;
		}
		HttpSession session=request.getSession();
		Object queryObj=session.getAttribute(spath);
		if(queryObj!=null){
			MutablePropertyValues values=new MutablePropertyValues((Map<String, Object>)queryObj);
			binder.bind(values);
		}
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}
}
