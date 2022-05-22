package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Area;
import com.tdh.pojo.User;
import com.tdh.service.AreaService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
public class AreaController extends BaseController {

    @Resource
    private AreaService areaService;


    @RequestMapping(value = Route.areaUrl.QUERY_LIST ,method= {RequestMethod.POST})
    public String list(HttpServletRequest request, Area params){
        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            this.initQuery(request, params);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            params.setHospital_id(login_user.getHospital_id());
            params.setArea_name(searchField);
            PageList<Map<String, Object>> pageList = areaService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,"执行异常，请联系管理员进行处理","");
        }
    }


    @RequestMapping(value = Route.areaUrl.ADD,method ={RequestMethod.POST})
    @ResponseBody
    public String insert(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONArray array = JSONArray.fromObject(data);
            //格式化
            List<Area> list = JSONArray.toList(array, new Area(), new JsonConfig());
            int count = areaService.queryCountServ(login_user.getHospital_id());
            if(!CollectionUtils.isEmpty(list)){
                areaService.mergeData(list,login_user,count);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }



    @RequestMapping(value = Route.areaUrl.DEL,method = {RequestMethod.POST})
    @ResponseBody
    public String delete(HttpServletRequest request){

        try{
            String area_id = request.getParameter("area_id");
            areaService.deleteServ(area_id);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


    @RequestMapping(value = Route.areaUrl.QUERY_AREAENUM_INFO,method = {RequestMethod.POST})
    @ResponseBody
    public String query_areaenum_info(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            List<Map<String ,Object>>list = areaService.queryAreaEmunInfoServ(login_user.getHospital_id());
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,list);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


}
