package com.tdh.controller;


import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.*;
import com.tdh.common.*;
import com.tdh.pojo.Goods;
import com.tdh.pojo.OaFtp;
import com.tdh.pojo.User;
import com.tdh.service.GoodsService;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class GoodsController extends BaseController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping(value = Route.GoodsUrl.QUERY_LIST, method = {RequestMethod.POST})
    public String list(HttpServletRequest request, Goods params) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            String searchField = request.getParameter(Config.searchField);
            params.setGoodsName(searchField);
            params.setCurPage(CommonUtil.getPage(request));
            params.setPageSize(CommonUtil.getLimit(request));
            PageList<Map<String, Object>> pageList = goodsService.selectPageMap(params);
            goodsService.handlePageListImageData(pageList);
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
    @RequestMapping(value = Route.GoodsUrl.ADD, method = {RequestMethod.POST})
    public String add(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {

            Goods param = (Goods) JsonUtil.toBean(request, new Goods());
            //参数重复校验
            if (ReturnUtils.ReturnParam.param_repeat.equals(goodsService.verifyAddRepeatParam(param.getGoodsName(), param.getGoodsCode()))) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.param_repeat, ReturnUtils.ReturnParam.param_repeat_msg, "");
            }
            goodsService.insertServ(user, param);
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
    @RequestMapping(value = Route.GoodsUrl.QUERY_DETAIL, method = {RequestMethod.POST})
    public String queryDetail(HttpServletRequest request) {
        try {
            String goodsId = request.getParameter("detail_id");
            if (StringUtils.isEmpty(goodsId)) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "create");
            }
            Map<String, Object> map = goodsService.queryGoodsDetailByIdServ(goodsId);
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
    @RequestMapping(value = Route.GoodsUrl.EDIT, method = {RequestMethod.POST})
    public String edit(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Config.loginUser);
        try {
            Goods param = (Goods) JsonUtil.toBean(request, new Goods());
            //参数重复校验
            if (ReturnUtils.ReturnParam.param_repeat.equals(goodsService.verifyEditRepeatParam(param.getGoodsName(), param.getGoodsCode()))) {
                return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.param_repeat, ReturnUtils.ReturnParam.param_repeat_msg, "");
            }
            goodsService.updateServ(user, param);
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
    @RequestMapping(value = Route.GoodsUrl.DEL, method = {RequestMethod.POST})
    public String del(HttpServletRequest request) {
        try {
            goodsService.delGoodsInfoByIdServ(request.getParameter("goods_id"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, "");
        } catch (Exception e) {
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }

    @RequestMapping(value = Route.GoodsUrl.QUERY_GOODS_BY_FACOTRY,method = {RequestMethod.POST})
    public String queryGoodsByFactory(HttpServletRequest request){
        try{
            List<Map<String ,Object>> result = goodsService.queryGoodsEnumByFactoryInfo(Config.integerClass.one,request.getParameter("factoryId"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, result);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


    @RequestMapping(value = Route.GoodsUrl.QUERY_GOODSENUM_BY_ID,method = {RequestMethod.POST})
    public String queryGoodsEnumById(HttpServletRequest request){
        try{
            List<Map<String ,Object>> result = goodsService.queryGoodsEnumByIdServ(request.getParameter("goodsId"));
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, result);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }


    @RequestMapping(value = Route.GoodsUrl.UPLOAD_GOODS, method = {RequestMethod.POST})
    public String upload(HttpServletRequest request) {
        String newName = "";
        try{
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while(iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    file.getBytes();
                    String fileName = file.getOriginalFilename();
                    if (!fileName.endsWith(".png")) {
                        //f返回文件格式有误
                    }
                    newName=  fileName;
                    FileUtils.createFile(PropertiesUtils.getProperties("img.path"));
                    File targetFile = new File(PropertiesUtils.getProperties("img.path"), newName);
                    FileOutputStream os = null;
                    InputStream in = null;
                    try {
                        os = new FileOutputStream(PropertiesUtils.getProperties("img.path")+"\\"+newName);
                        //拿到上传文件的输入流
                        in = file.getInputStream();
//                        以写字节的方式写文件
                        int b = 0;
                        while((b=in.read()) != -1){
                            os.write(b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally{
                        os.flush();
                        os.close();
                        in.close();
                    }
                }
            }
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.success, ReturnUtils.ReturnParam.success_msg, PropertiesUtils.getProperties("img.path")+"\\"+newName);
        }catch(Exception e){
            return ReturnUtils.ReturnObj(ReturnUtils.ReturnParam.fail, ReturnUtils.ReturnParam.exception_msg, "");
        }
    }
}


