package com.tdh.controller;

import com.tdh.common.*;
import com.tdh.pojo.Menu;
import com.tdh.pojo.User;
import com.tdh.service.MenuChildService;
import com.tdh.service.MenuService;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class MenuRestController extends BaseController {

    @Resource
    private MenuService menuService;

    @Resource
    private MenuChildService menuChildService;

    @RequestMapping(value = Route.menuUrl.GENERATE_DIRECTORY,method = {RequestMethod.GET})
    public String addSystemTable(HttpServletRequest request, ModelMap modleMap){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        //处理参数
        try{
            Map<String ,Object> map= menuService.handleCreateTable(login_user);
            String data = CommonUtil.getPrettyGsonStr(map);
            return data;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    @RequestMapping(value = Route.menuUrl.QUERY_LIST,method = {RequestMethod.GET})
    public Object queryTreeObject(HttpServletRequest request){

        //处理参数
        try{
            Map<String ,Object> reuslt = menuService.handleQueryMenuList();
            String str = CommonUtil.getPrettyGsonStr(reuslt);
            return str;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


    @RequestMapping(value = Route.menuUrl.QUERY_DETAIL,method = {RequestMethod.POST})
    public String queryMenuDetail(HttpServletRequest request){

        //处理参数
        try{
            String data = request.getParameter(Config.data);
            Menu menu = menuService.queryInfobyId(data);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,menu);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }




    @RequestMapping(value = Route.menuUrl.ADD_UPDATE_MENU_INFO,method = {RequestMethod.POST})
    public String addAndUpdateMneu(HttpServletRequest request){

        try{
            User login_user = (User) request.getSession().getAttribute(Config.loginUser);
            if(StringUtils.isNotEmpty(String.valueOf(request.getParameter("id")))){
                Menu parentMenu = menuService.queryMenuInfoByNameServ(String.valueOf(request.getParameter("parent_menu")));
                menuService.update(request,login_user.getUser_code(),parentMenu);
            }else{
                String req = request.getParameter("main_id");
                if(StringUtils.isEmpty(String.valueOf(request.getParameter("parent_menu")))){
                    Menu parentMenu = menuService.queryMenuInfoByNameServ(String.valueOf(request.getParameter("parent_menu")));
                    menuService.insert(request,login_user.getUser_code(),parentMenu.getId(),Config.strClassNum.zero);
                }else{
                    Menu parentMenu = menuService.queryMenuInfoByNameServ(String.valueOf(request.getParameter("parent_menu")));
                    int level = Integer.parseInt(parentMenu.getLevel_flag())+Config.integerClass.one;
                    menuService.insert(request,login_user.getUser_code(),parentMenu.getId(), String.valueOf(level));

                }
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

}
