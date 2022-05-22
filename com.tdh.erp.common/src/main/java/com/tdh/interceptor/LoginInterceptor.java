package com.tdh.interceptor;

import com.tdh.common.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
	
	private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	//ControllerController逻辑执行之前
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("preHandle....");
		Object user = request.getSession().getAttribute("loginUser");
		if (user == null) {
 			request.setAttribute("msg", "无权限请先登录");
 			request.getRequestDispatcher("/login.do").forward(request, response);
 			return false;
		} else {
            request.getSession().setAttribute("sysTrackCode",CommonUtil.createUUIDNoFlag());
            return true;
		}
	}
	
	//Controller逻辑执行完毕但是视图解析器还为进行解析之前
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    	log.info("postHandle....");
;
    }
    
    //Controller逻辑和视图解析器执行完毕
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    	log.info("afterCompletion....");
    }
}
