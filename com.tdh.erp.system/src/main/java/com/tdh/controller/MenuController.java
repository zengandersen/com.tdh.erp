package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Menu;
import com.tdh.pojo.MenuChild;
import com.tdh.pojo.User;
import com.tdh.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 */
@Controller
public class MenuController extends BaseController {
	
	@Resource
	private MenuService menuService;

	/**
	 * 菜单管理查询列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/menu-list.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Menu params) throws Exception {

		try{
			User login_user = (User) request.getSession().getAttribute(Config.loginUser);
			this.initQuery(request, params);
			modelMap.addAttribute("params", params);
			return "system/menu/menu-list";
		}catch(Exception e){
			return "message/error";
		}
	}
	

	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/menu-edit.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		try{
			String id = request.getParameter("id");
				Menu params = menuService.queryInfobyId(id);
				List<Map<String ,String>> iconList = menuService.queryIconInfo();
				modelMap.addAttribute("icon", iconList);
				modelMap.addAttribute("params", params);
			return "system/menu/menu-edit";
		}catch(Exception e){
			return "message/error";
		}
	}




	
	/**
	 * 禁用
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu-disable.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String disable(HttpServletRequest request, ModelMap modelMap) throws Exception {
		User login_user = (User) request.getSession().getAttribute(Config.loginUser);
		menuService.updateStatusById(String.valueOf(request.getParameter("id")), Config.statusClass.close,login_user.getUser_code());
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
	@RequestMapping(value = "/menu-enable.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String enable(HttpServletRequest request, ModelMap modelMap) throws Exception {
		User login_user = (User) request.getSession().getAttribute(Config.loginUser);
		menuService.updateStatusById(String.valueOf(request.getParameter("id")), Config.statusClass.open,login_user.getUser_code());
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
	@RequestMapping(value =Route.menuUrl.DEL ,method= {RequestMethod.POST})
	public String delete(HttpServletRequest request, ModelMap modelMap) throws Exception {
		try{
			menuService.deleteById(String.valueOf(request.getParameter("data")));
			return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");

		}catch(Exception e){
			return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
		}


	}


	/**
	 * 获取未绑定菜单
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu-notbind.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String queryMenuInfoNotBind(HttpServletRequest request, ModelMap modelMap) throws Exception {
		List<MenuChild> list = menuService.queryMenuInfoNotBind();
        List<Map<String ,String>> icon= menuService.queryIconInfo();
        modelMap.addAttribute("icon", icon);
		modelMap.addAttribute("params",list);
		return "system/menu/menu-notbind";
	}

	/**
	 * 获取已绑定菜单
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu-bind.do" ,method= {RequestMethod.POST,RequestMethod.GET})
	public String queryMenuInfoBind(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String id = request.getParameter("id");
		List<MenuChild> list = menuService.queryMenuInfoBind(id);
		modelMap.addAttribute("parent_id",id);
		modelMap.addAttribute("params",list);
		return "system/menu/menu-bind";
	}

	@RequestMapping(value = "/save-bindInfo.do",method = {RequestMethod.POST})
	public String saveBindInfo(HttpServletRequest request, ModelMap modleMap){

		try{
			User login_user = (User) request.getSession().getAttribute(Config.loginUser);
			String[] ids = request.getParameterValues("ids");
			String parent_id = request.getParameter("parent_id");
			menuService.updateParentIdById(login_user.getUser_code(),parent_id,ids);
			modleMap.put("message","操作成功");
			return "message/success";
		}catch(Exception e){
			return "message/error";
		}
	}



}
