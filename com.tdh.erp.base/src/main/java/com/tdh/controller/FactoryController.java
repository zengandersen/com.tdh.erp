package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Dict;
import com.tdh.pojo.Factory;
import com.tdh.pojo.User;
import com.tdh.service.DictService;
import com.tdh.service.FactoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class FactoryController extends BaseController {

    @Resource
    private FactoryService factoryService;

    /**
     * 获取厂家列表数据
     * @param request
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Route.FactoryUrl.QUERY_LIST,method = {RequestMethod.POST})
    public String list(HttpServletRequest request, Factory params){
        User loginUser = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            params.setFactoryName(searchField);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = factoryService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


    /**
     * 获取明细数据
     * @param request
     * @return
     */
    @RequestMapping(value = Route.FactoryUrl.QUERY_DETAIL,method = {RequestMethod.POST})
    public String queryDetail(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String factoryId = request.getParameter("factory_id");
            if(StringUtils.isEmpty(factoryId)){
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
            }
            Map<String ,Object> map = factoryService.queryFactoryDetailByIdServ(factoryId);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,map);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }



    /**
     * 添加
     * @param request
     * @return
     */
    @RequestMapping(value = Route.FactoryUrl.EDIT,method = {RequestMethod.POST})
    public String edit(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONObject object = JSONObject.fromObject(data);
            Factory factory = (Factory) JSONObject.toBean(object, new Factory(), new JsonConfig());
            if(StringUtils.isNotEmpty(factory.getFactoryId())){
                factoryService.updateServ(loginUser,factory);
            }else{
                int orderNum = factoryService.queryOrderNum();
                factoryService.insertServ(loginUser,factory,orderNum);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }




    /**
     * 行修改
     * @param request
     * @return
     */
    @RequestMapping(value = Route.FactoryUrl.MARGE,method = {RequestMethod.POST})
    public String marge(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String date = request.getParameter("data");

            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    /**
     * 行删除
     * @param request
     * @return
     */
    @RequestMapping(value = Route.FactoryUrl.DEL,method = {RequestMethod.POST})
    public String del(HttpServletRequest request){
        try{
            factoryService.delServ(request.getParameter("factory_id"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    @RequestMapping(value = Route.FactoryUrl.ENUM, method = {RequestMethod.POST})
    public String queryEnum(HttpServletRequest request){
        try{
            List<Map<String ,Object>> result = factoryService.queryFactoryInfoEnumServ();
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,result);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }


}
