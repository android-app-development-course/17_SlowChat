package com.chat.controller;

import com.chat.entity.User;
import com.chat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 利用用户邮箱获取user的信息
     * @param email 用户邮箱
     * @return 用户信息
     */
    @RequestMapping(value = "/user/getUserMessage.do" ,method = RequestMethod.GET)
    @ResponseBody
    public User getUser(String email){
        User user=userService.getUserMessage(email);
        if(user==null){
            return null;
        }

        return user;
    }
}
