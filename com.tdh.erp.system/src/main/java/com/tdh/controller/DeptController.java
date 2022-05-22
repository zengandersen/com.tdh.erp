package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Dept;
import com.tdh.pojo.User;
import com.tdh.service.DeptService;

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
public class DeptController extends BaseController {

    @Resource
    private DeptService deptService;


    @RequestMapping(value = Route.deptUrl.QUERY_LIST ,method= {RequestMethod.POST})
    public String list(HttpServletRequest request, Dept params){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            this.initQuery(request, params);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            params.setHospital_id(login_user.getHospital_id());
            params.setDept_name(searchField);
            PageList<Map<String, Object>> pageList = deptService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,"执行异常，请联系管理员进行处理","");
        }
    }


    @RequestMapping(value = Route.deptUrl.ADD,method ={RequestMethod.POST})
    @ResponseBody
    public String insert(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONArray array = JSONArray.fromObject(data);
            //格式化
            List<Dept> list = JSONArray.toList(array, new Dept(), new JsonConfig());
            int count = deptService.queryCountServ(login_user.getHospital_id());
            if(!CollectionUtils.isEmpty(list)){
                deptService.mergeData(list,login_user,count);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }



    @RequestMapping(value = Route.deptUrl.DEL,method = {RequestMethod.POST})
    @ResponseBody
    public String delete(HttpServletRequest request){

        try{
            String dept_id = request.getParameter("dept_id");
            deptService.deleteServ(dept_id);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


    @RequestMapping(value = Route.deptUrl.QUERY_DEPTENUM_INFO,method = {RequestMethod.POST})
    @ResponseBody
    public String query_deptenum_info(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            List<Map<String ,Object>>list = deptService.queryDeptEmunInfoServ(login_user.getHospital_id());
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,list);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


}
