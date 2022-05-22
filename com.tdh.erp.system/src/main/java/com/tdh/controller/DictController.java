package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Dict;
import com.tdh.pojo.User;
import com.tdh.service.DictService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
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
public class DictController extends BaseController {

    @Resource
    private DictService dictService;

    /**
     * 字典列表信息获取
     * @param request
     * @param response
     * @param modelMap
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Route.dictUrl.QUERY_LIST,method = {RequestMethod.POST})
    public String list(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Dict params) throws Exception{

        try{
            User login_user = (User) request.getSession().getAttribute(Config.loginUser);
            params.setHospital_id(login_user.getHospital_id());
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));

            PageList<Map<String, Object>> pageList = dictService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    /**
     * 字典明细数据新增或更新
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value=Route.dictUrl.ADD,method = {RequestMethod.POST})
    @ResponseBody
    public String add (HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONArray array = JSONArray.fromObject(data);
            List<Dict> list = JSONArray.toList(array, new Dict(), new JsonConfig());
            int count = dictService.queryCountServ(login_user.getHospital_id());
            if(!CollectionUtils.isEmpty(list)){
                dictService.mergeData(list,login_user,count);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    /**
     * 字典明细数据删除
     * @param request
     * @return
     */
    @RequestMapping(value = Route.dictUrl.DEL,method = {RequestMethod.POST})
    public String delete(HttpServletRequest request){

        try{
            String dict_id = request.getParameter("dict_id");
            dictService.deleteServ(dict_id);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    @RequestMapping (value= Route.dictUrl.QUERY_DICT_DETAILINFO,method = {RequestMethod.POST})
    @ResponseBody
    public String queryDictDetailInfo(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String dict_type_code_arr = request.getParameter("data");
            List<Map<String ,Object>> list = dictService.queryDictDetail(login_user.getHospital_id(),dict_type_code_arr);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,list);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }
}
