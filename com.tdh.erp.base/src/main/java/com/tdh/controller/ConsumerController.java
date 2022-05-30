package com.tdh.controller;


import com.tdh.common.*;
import com.tdh.pojo.Consumer;
import com.tdh.pojo.Goods;
import com.tdh.pojo.User;
import com.tdh.service.ConsumerService;
import com.tdh.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class ConsumerController extends BaseController {

    @Resource
    private ConsumerService consumerService;

    @RequestMapping(value = Route.ConsumerUrl.QUERY_LIST, method = {RequestMethod.POST})
    public String list(HttpServletRequest request, Consumer params) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            String searchField = request.getParameter(Config.searchField);
            params.setName(searchField);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = consumerService.selectPageMap(params);
            String result = ReturnUtils.ReturnPageObj(pageList.getDataList(), pageList.getCount());
            return result;
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 新增
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.ConsumerUrl.ADD, method = {RequestMethod.POST})
    public String add(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            Consumer params = (Consumer) JsonUtil.toBean(request, new Consumer());
            consumerService.insertServ(user, params);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 获取明细数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.ConsumerUrl.QUERY_DETAIL, method = {RequestMethod.POST})
    public String queryDetail(HttpServletRequest request) {
        try {
            String consumerId = request.getParameter("detail_id");
            if (StringUtils.isEmpty(consumerId)) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "create");
            }
            Map<String, Object> map = consumerService.queryConsumerDetailByIdServ(consumerId);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, map);
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.ConsumerUrl.EDIT, method = {RequestMethod.POST})
    public String edit(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            Consumer param = (Consumer) JsonUtil.toBean(request, new Consumer());
            consumerService.updateServ(user, param);
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.ConsumerUrl.DEL, method = {RequestMethod.POST})
    public String del(HttpServletRequest request) {
        try {
            consumerService.delServ(request.getParameter("consumer_id"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    /**
     * 获取明细数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = Route.ConsumerUrl.QUERY_ENUM, method = {RequestMethod.POST})
    public String queryEnum(HttpServletRequest request){
        try{
            List<Map<String ,Object>> result = consumerService.queryConsumerEnumAll();
            return ReturnUtils.ReturnPageObj(result,result.size());
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


}


