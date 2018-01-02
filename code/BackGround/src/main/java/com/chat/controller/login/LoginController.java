package com.chat.controller.login;

import com.chat.application.Response;
import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.service.login.LoginService;
import com.chat.service.login.RegisterService;
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
    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/login/login.do",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response, String email, String pwd){
        if(!loginService.isPass(email,pwd)){
            return Response.getFailureResponse("账号或密码不正确！");
        }

        //添加cookie
        User user= userDao.getUserByEmail(email);
        loginService.addCookie(request,response,user);

        return Response.getsuccessResponse("登录成功！");
    }

    @RequestMapping(value = "/login/register.do",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> register(String email,String pwd){
        if(!registerService.isEmailFit(email)){
            return Response.getFailureResponse("邮箱格式不符合要求");
        }

        if(!registerService.isEmailUnregistered(email)){
            return Response.getFailureResponse("该邮箱已注册");
        }

        if(!registerService.isPwdFit(pwd)){
            return Response.getFailureResponse("密码格式不符合要求");
        }

        //创建user并且把user添加到数据库里面
        User user=SpringUtil.getBean(User.class);
        user.setEmail(email);
        user.setPwd(pwd);

        userDao.add(user);
        return Response.getsuccessResponse();
    }
}
