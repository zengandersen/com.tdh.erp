package com.tdh.controller;

import com.tdh.common.*;
import com.tdh.pojo.Hospital;
import com.tdh.pojo.User;
import com.tdh.service.HospitalService;
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
public class HospitalController extends BaseController {

    @Resource
    private HospitalService hospitalService;

    /**
     * 医院管理查询列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Route.hospitalUrl.QUERY_LIST ,method= {RequestMethod.POST})
    public String list(HttpServletRequest request,Hospital params) throws Exception {

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            this.initQuery(request, params);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            params.setHospital_id(login_user.getHospital_id());
            params.setHospital_code(searchField);
            PageList<Map<String, Object>> pageList = hospitalService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,"执行异常，请联系管理员进行处理","");
        }
    }

    @RequestMapping(value=Route.hospitalUrl.ADD,method = {RequestMethod.POST})
    @ResponseBody
    public String add (HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONArray array = JSONArray.fromObject(data);
            List<Hospital> list = JSONArray.toList(array, new Hospital(), new JsonConfig());
            if(!CollectionUtils.isEmpty(list)){
                hospitalService.mergeData(list,login_user);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }



    @RequestMapping(value=Route.hospitalUrl.DEL,method = {RequestMethod.POST})
    @ResponseBody
    public String delete(HttpServletRequest request) {

        try{
            String dept_id = request.getParameter("dept_id");
            hospitalService.deleteHospitalInfoServ(dept_id);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    @RequestMapping(value = Route.hospitalUrl.QUERY_HOSPITALNUM_INFO,method = {RequestMethod.POST})
    @ResponseBody
    public String query_hospitalenum_info(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            List<Map<String ,Object>>list = hospitalService.queryHospitalEmunInfoServ(login_user.getHospital_id());
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,list);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


}
