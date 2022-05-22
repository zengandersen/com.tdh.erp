package com.tdh.controller;

import com.tdh.common.*;
import com.tdh.pojo.Role;
import com.tdh.pojo.User;
import com.tdh.service.RoleService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;



    @RequestMapping(value = Route.roleUrl.QUERY_LIST ,method= {RequestMethod.POST})
    public String list(HttpServletRequest request,Role params) throws Exception {

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            this.initQuery(request, params);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            params.setRole_code(searchField);
            params.setHospital_id(login_user.getHospital_id());
            PageList<Map<String, Object>> pageList = roleService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,"执行异常，请联系管理员进行处理","");
        }
    }

    @RequestMapping(value=Route.roleUrl.EDIT,method = {RequestMethod.POST})
    public String edit (HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONArray array = JSONArray.fromObject(data);
            List<Role> list = JSONArray.toList(array, new Role(), new JsonConfig());
            int index= roleService.queryCountServ(login_user.getHospital_id());
            if(!CollectionUtils.isEmpty(list)){
                roleService.mergeData(list,login_user,index);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    @RequestMapping(value=Route.roleUrl.DEL,method = {RequestMethod.POST})
    public String delete(HttpServletRequest request) {

        try{
            String role_id = request.getParameter("role_id");
            roleService.deleteServ(role_id);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    @RequestMapping(value=Route.roleUrl.QUERY_ENUM,method = {RequestMethod.POST})
    public String queryRoleEnum(HttpServletRequest request) {

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            List<Map<String ,Object>> result = roleService.queryRoleEnumByIdServ(login_user.getHospital_id(),login_user.getCompound_id());
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,result);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }
}
