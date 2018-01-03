package com.chat.service.login;

import com.chat.dao.UserDao;
import com.chat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private UserDao userDao;

    public boolean isPass(String email, String pwd) {
        User user=userDao.getUserByEmail(email);
        if(user==null){
            return false;
        }

        if(!user.getPwd().equals(pwd)){
            return false;
        }

        return true;
    }

    public void addCookie(HttpServletRequest request,HttpServletResponse response,User user) {
        //获取session作为登录凭证
        String sessionId=request.getSession().getId();
        user.setSignature(sessionId);
        userDao.update(user);

        //把cookie写入到response
        Cookie cookie=new Cookie("username",user.getEmail());
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie=new Cookie("SID",sessionId);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
