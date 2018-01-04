package com.chat.controller;

import com.chat.util.ResponseUtil;
import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.service.login.LoginService;
import com.chat.service.login.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RegisterService registerService;

    /**
     * 登录接口
     * @param request
     * @param response
     * @param email 用户邮箱
     * @param pwd 用户密码
     * @return
     */
    @RequestMapping(value = "/login/login.do",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response,
                                    String email, String pwd){
        if(!loginService.isPass(email,pwd)){
            return ResponseUtil.getFailureResponse("账号或密码不正确！");
        }

        //添加cookie
        User user= userDao.getUserByEmail(email);
        loginService.addCookie(request,response,user);

        return ResponseUtil.getSuccessResponse("登录成功！");
    }

    /**
     * 注册接口
     * @param email 用户邮箱
     * @param pwd 用户密码
     * @param username 用户名
     * @return
     */
    @RequestMapping(value = "/login/register.do",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> register(String email,String pwd,String username){
        if(!registerService.isEmailFit(email)){
            return ResponseUtil.getFailureResponse("邮箱格式不符合要求");
        }

        if(!registerService.isEmailUnregistered(email)){
            return ResponseUtil.getFailureResponse("该邮箱已注册");
        }

        if(!registerService.isPwdFit(pwd)){
            return ResponseUtil.getFailureResponse("密码格式不符合要求");
        }

        //添加到user
        registerService.addUser(email,pwd,username);

        return ResponseUtil.getSuccessResponse("注册成功");
    }
}
