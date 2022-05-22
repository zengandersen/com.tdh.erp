package com.tdh.controller;



import com.tdh.common.*;
import com.tdh.pojo.DictType;
import com.tdh.pojo.User;
import com.tdh.service.DictTypeService;
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
public class DictTypeController extends BaseController {



    @Resource
    private DictTypeService dictTypeService;



    /**
     * 查询字典类型列表数据
     * @param request
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Route.dictTypeUrl.QUERY_LIST ,method= {RequestMethod.POST})
    public String list(HttpServletRequest request, DictType params){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            this.initQuery(request, params);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            params.setHospital_id(login_user.getHospital_id());
            params.setDict_type_name(searchField);
            PageList<Map<String, Object>> pageList = dictTypeService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(),pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,"执行异常，请联系管理员进行处理","");
        }
    }


    /**
     * 新增修改数据
     * @param request
     * @return
     */
    @RequestMapping(value = Route.dictTypeUrl.ADD,method ={RequestMethod.POST})
    @ResponseBody
    public String insert(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String data = request.getParameter("data");
            JSONArray array = JSONArray.fromObject(data);
            //格式化
            List<DictType> list = JSONArray.toList(array, new DictType(), new JsonConfig());
            int count = dictTypeService.queryCountServ(login_user.getHospital_id());
            if(!CollectionUtils.isEmpty(list)){
                    dictTypeService.mergeData(list,login_user,count);
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }

    /**
     * 删除数据
     * @param request
     * @return
     */
    @RequestMapping(value = Route.dictTypeUrl.DEL,method = {RequestMethod.POST})
    @ResponseBody
    public String delete(HttpServletRequest request){

        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String hospital_id = login_user.getHospital_id();
            String dict_type_code = request.getParameter("dict_type_code");
            dictTypeService.deleteServ(dict_type_code,hospital_id);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success,ReturnUtils.ReturnParam.success_msg,"");
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail,ReturnUtils.ReturnParam.exception_msg,"");
        }
    }
}
