package com.chat.controller.login;

import com.chat.application.Response;
import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.service.login.LoginService;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;
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

    @RequestMapping(value = "/login/login.do",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response, String email, String pwd){
        HibernateUtil.getCurrentSession().beginTransaction();
        Map<String,Object> map;

        if(!loginService.isPass(email,pwd)){
            map=Response.getFailureResponse("账号或密码不正确！");
        }

        map=Response.getsuccessResponse("登录成功！");

        //添加cookie
        User user= userDao.getUserByEmail(email);
        loginService.addCookie(request,response,user);

        HibernateUtil.getCurrentSession().getTransaction().commit();
        return map;
    }
}
