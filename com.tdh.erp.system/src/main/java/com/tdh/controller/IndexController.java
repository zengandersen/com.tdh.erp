package com.tdh.controller;



import com.tdh.common.Config;
import com.tdh.common.Md5API;
import com.tdh.pojo.User;
import com.tdh.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Configuration
public class IndexController {


    @Resource
    private UserService userService;

    @RequestMapping(value = "/loginReq.do",method= {RequestMethod.POST,RequestMethod.GET})
    public String login (HttpServletRequest request,
                         HttpServletResponse response, ModelMap modelMap, User params, HttpSession session) throws Exception {
        String userCode = request.getParameter("user_code");
        String userPwd = Md5API.passwordCreate(request.getParameter("user_pwd"));
        User sysUser = userService.queryUserObjectByUserCode(userCode,userPwd);
        if(null != sysUser){
            if (userCode.equals(sysUser.getUser_code()) && userPwd.equals(sysUser.getUser_pwd())) {
                session.setAttribute("loginUser", sysUser);
                modelMap.addAttribute("user_name", sysUser.getUser_name());
                return "index";
            } else {
                modelMap.addAttribute("error", "用户名或密码错误！");
                return "login";
            }
        }else{
            modelMap.addAttribute("error", "用户名或密码错误！");
            return "login";
        }
    }

    /**
     * 登录跳转
     * @param request
     * @param response
     * @param modelMap
     * @param params
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login.do",method  ={RequestMethod.GET})
    public String redirectLogin(HttpServletRequest request,
                                HttpServletResponse response, ModelMap modelMap, User params, HttpSession session) throws Exception {
        return "login";
    }

    /**
     * 退出系统
     * @param request
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/logout.do",method = {RequestMethod.GET})
    public String logout(HttpServletRequest request, HttpSession session,
                         ModelMap modelMap) {
        session.removeAttribute(Config.loginUser);
        return "login";
    }

    /**
     * 用户登录名唯一性确认
     * @param request
     * @param modelMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/user-validate")
    public void validate(HttpServletRequest request, ModelMap modelMap,HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("GBK");
        response.setContentType("application/text; charset=gbk");
        String user_code = request.getParameter("user_code");
        if(userService.findByUser(user_code)==null){
            response.getWriter().write("true");
        }else{
            response.getWriter().write("false");
        }
    }
    /**
     * 访问判断是否存有session信息，
     * 分情况跳转页面
     * @param model
     * @param modelMap
     * @param request
     * @return
     */
    @GetMapping(value = "/")
    public String defaultPath(Model model, ModelMap modelMap,HttpServletRequest request) {
        User login_user = (User) request.getSession().getAttribute(Config.loginUser);
        if(null != login_user){
            modelMap.addAttribute("user_name", login_user.getUser_name());
            return "index";
        }else{
            return "login";
        }
    }


}
