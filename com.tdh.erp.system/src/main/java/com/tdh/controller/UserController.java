package com.tdh.controller;

import com.tdh.common.*;
import com.tdh.pojo.User;
import com.tdh.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@org.springframework.web.bind.annotation.RestController
public class UserController extends BaseController {

    @Resource
    private UserService userService;



    @RequestMapping(value = Route.userUrl.QUERY_LIST ,method= {RequestMethod.POST})
    public String list(HttpServletRequest request,User params) throws Exception {

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            this.initQuery(request, params);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            params.setUser_code(searchField);
            params.setHospital_id(login_user.getHospital_id());
            PageList<Map<String, Object>> pageList = userService.selectPageMap(params);
            userService.handlePasword(pageList);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,"执行异常，请联系管理员进行处理","");
        }
    }

    @RequestMapping(value=Route.userUrl.ADD,method = {RequestMethod.POST})
    @ResponseBody
    public String add (HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONArray array = JSONArray.fromObject(data);
            List<User> list = JSONArray.toList(array, new User(), new JsonConfig());
            if(!CollectionUtils.isEmpty(list)){
                userService.mergeData(list,login_user);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    @RequestMapping(value=Route.userUrl.DEL,method = {RequestMethod.POST})
    @ResponseBody
    public String delete(HttpServletRequest request) {

        try{
            String user_id = request.getParameter("user_id");
            userService.deleteDeptInfoByIdServ(user_id);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }
}
