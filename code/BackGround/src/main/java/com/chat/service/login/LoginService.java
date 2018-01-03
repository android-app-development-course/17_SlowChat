package com.chat.service.login;

import com.chat.entity.User;
import org.springframework.http.server.ServerHttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    /**
     * 判断登录是否通过
     * @param email 登录邮箱
     * @param pwd 登录密码
     * @return 如果通过则返回true，否则返回false
     */
    boolean isPass(String email,String pwd);

    /**
     * 给通过登录的用户添加认证后的cookie
     * @param request
     * @param response
     * @param user 已经认证通过登录的user对象
     */
    void addCookie(HttpServletRequest request, HttpServletResponse response, User user);
}
