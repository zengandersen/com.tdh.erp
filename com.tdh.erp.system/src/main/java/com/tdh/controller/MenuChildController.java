package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Menu;
import com.tdh.pojo.MenuChild;
import com.tdh.pojo.User;
import com.tdh.service.MenuChildService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 */

@Controller
public class MenuChildController extends BaseController {
	
	@Resource
	private MenuChildService menuChildService;

	/**
	 * 菜单管理查询列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/menuchild-list.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, MenuChild params) throws Exception {

		try{
			User login_user = (User) request.getSession().getAttribute(Config.loginUser);
			this.initQuery(request, params);
			PageList<Map<String, Object>> pageList = menuChildService.selectPageMap(params);
			modelMap.addAttribute("pageList", pageList);
			modelMap.addAttribute("params", params);
			return "system/menu/menuchild-list";
		}catch(Exception e){
			return "message/error";
		}
	}
	
	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/menuchild-add.do" ,method= {RequestMethod.GET,RequestMethod.POST})
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Menu params) {
		try{
			String iconid = PropertiesUtils.getProperties("icon");
			List<Menu> menuIndex = menuChildService.querMenuInfo();
			String []arr = iconid.split(",");
			List<Map<String,Object>> result =new ArrayList<>();
			for(String str :arr){
				Map<String ,Object> map = new HashMap<>();
				map.put("iconid",str);
				result.add(map);
			}
			modelMap.addAttribute("parents", menuIndex);
			modelMap.addAttribute("icon", result);
			return "system/menu/menuchild-edit";
		}catch (Exception e){
			return "message/error";
		}
	}
	


	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/menuchild-save.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String save(HttpServletRequest request, HttpServletResponse response, MenuChild params, ModelMap modelMap) {
		User login_user = (User) request.getSession().getAttribute(Config.loginUser);

		if(StringUtils.isNotEmpty(String.valueOf(request.getParameter("id")))){
			menuChildService.update(request,login_user.getUser_code());
		}else{
			menuChildService.insert(request,login_user.getUser_code());
		}
		modelMap.addAttribute("params", params);
		modelMap.put("message","操作成功");
		return "message/success";
	}
	
	/**
	 * 禁用
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menuchild-disable.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String disable(HttpServletRequest request, ModelMap modelMap) throws Exception {
		User login_user = (User) request.getSession().getAttribute(Config.loginUser);
		menuChildService.updateStatusById(String.valueOf(request.getParameter("id")), Config.statusClass.close,login_user.getUser_code());
		modelMap.put("message","操作成功");
		return "message/success";
	}

	/**
	 * 启用
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menuchild-enable.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String enable(HttpServletRequest request, ModelMap modelMap) throws Exception {
		User login_user = (User) request.getSession().getAttribute(Config.loginUser);
		menuChildService.updateStatusById(String.valueOf(request.getParameter("id")), Config.statusClass.open,login_user.getUser_code());
		modelMap.put("message","操作成功");
		return "message/success";
	}


	/**
	 * 删除
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menuchild-delete.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String delete(HttpServletRequest request, ModelMap modelMap) throws Exception {
		menuChildService.deleteById(String.valueOf(request.getParameter("id")));
		modelMap.put("message","操作成功");
		return "message/success";
	}



}
