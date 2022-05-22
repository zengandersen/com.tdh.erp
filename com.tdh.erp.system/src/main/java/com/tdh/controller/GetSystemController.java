package com.tdh.controller;


import com.tdh.common.Route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class GetSystemController {

    @RequestMapping(value = Route.dictUrl.TO_DICT_LIST,method = {RequestMethod.GET})
    public String toDictlist(){
        return Route.dictUrl.DICT_LIST_URL;
    }

    @RequestMapping(value = Route.dictTypeUrl.TO_DICT_TYPE_LIST, method = {RequestMethod.GET})
    public String toDictTypeList(){
        return Route.dictTypeUrl.DICT_TYPE_LIST_URL;
    }

    @RequestMapping(value = Route.hospitalUrl.TO_HOSPITAL_URL,method = {RequestMethod.GET})
    public String toHospitallist(){
        return Route.hospitalUrl.HOSPITAL_LIST_URL;
    }

    //分院管理---get方法在此填写
    @RequestMapping(value = Route.compoundUrl.TO_COMPOUND_URL,method = {RequestMethod.GET})
    public String toCompoundlist(){
        return Route.compoundUrl.COMPOUND_LIST_URL;
    }

    //诊区管理---get方法在此填写
    @RequestMapping(value = Route.areaUrl.TO_AREA_URL,method = {RequestMethod.GET})
    public String toArealist(){
        return Route.areaUrl.AREA_LIST_URL;
    }

    //科室管理---get方法在此填写
    @RequestMapping(value = Route.deptUrl.TO_DEPT_URL,method = {RequestMethod.GET})
    public String toDeptlist(){
        return Route.deptUrl.DEPT_LIST_URL;
    }

    @RequestMapping(value = Route.userUrl.TO_USER_URL,method = {RequestMethod.GET})
    public String toUserList(){return Route.userUrl.USER_LIST_URL;}

    @RequestMapping(value = Route.menuUrl.TO_MENU_ADD,method = {RequestMethod.GET})
    public String toAddMenu (){ return Route.menuUrl.MENU_ADD_URL;}

    @RequestMapping(value = Route.roleUrl.TO_ROLE_URL,method = {RequestMethod.GET})
    public String toRole (){return Route.roleUrl.ROLE_LIST_URL;}

    @RequestMapping(value = Route.roleUrl.TO_ROLE_BIND_URL,method = {RequestMethod.GET})
    public String toRoleBind (){return Route.roleUrl.ROLE_BOND_URL;}


}
