package com.tdh.controller;

import com.tdh.common.*;
import com.tdh.pojo.RoleBind;
import com.tdh.pojo.User;
import com.tdh.service.MenuService;
import com.tdh.service.RoleBindService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
public class RoleBindController extends BaseController {

    @Resource
    private RoleBindService roleBindService;

    @Resource
    private MenuService menuService;

    @RequestMapping(value=Route.roleUrl.QUERY_BIND,method = {RequestMethod.GET})
    public String queryBind(HttpServletRequest request) {
        try{
            String role_id = request.getParameter("data");
            Map<String ,Object> menuMap = menuService.handleQueryMenuList();
            List<RoleBind> roleList = roleBindService.findBindInfoByIdServ(role_id);
            Map<String ,Object> list = roleBindService.handleBindMenuData(menuMap,roleList);
            String str = CommonUtil.getPrettyGsonStr(list);
            return str;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


    @RequestMapping(value=Route.roleUrl.TO_DO_BIND,method = {RequestMethod.POST})
    public String bindMenu(HttpServletRequest request) {

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String role_id = request.getParameter("data");
            String ids = request.getParameter("ids");
            roleBindService.handleBind(role_id,ids,login_user);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


}
