package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Input;
import com.tdh.pojo.User;
import com.tdh.service.InputService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class InputController extends BaseController {

    @Resource
    private InputService inputService;

    @RequestMapping(value = Route.InputUrl.QUERY_LIST,method = {RequestMethod.POST})
    public String list(HttpServletRequest request,Input params){
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try{
            String searchField = request.getParameter(Config.searchField);
            params.setMealName(searchField);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = inputService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(), pageList.getCount());
            return result;
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    @RequestMapping(value = Route.InputUrl.ADD,method = {RequestMethod.POST})
    public String add(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            Input param = (Input) JsonUtil.toBean(request, new Input());
            inputService.insertServ(user, param);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


    /**
     * 获取入库明细
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.InputUrl.QUERY_DETAIL, method = {RequestMethod.POST})
    public String queryDetail(HttpServletRequest request) {
        try {
            String inputId = request.getParameter("detail_id");
            if (StringUtils.isEmpty(inputId)) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "create");
            }
            Map<String, Object> map = inputService.queryInputDetailInfoByIdServ(inputId);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, map);

        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

}


