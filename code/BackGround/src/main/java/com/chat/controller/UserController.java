package com.chat.controller;

import com.chat.dao.TagDao;
import com.chat.entity.User;
import com.chat.service.user.UserService;
import com.chat.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TagDao tagDao;

    /**
     * 利用用户邮箱获取user的信息
     * @param email 用户邮箱
     * @return 用户信息
     */
    @RequestMapping(value = "/user/getUserMessage.do" ,method = RequestMethod.GET)
    @ResponseBody
    public User getUserMessage(String email){
        User user=userService.getUserMessage(email);
        if(user==null){
            return null;
        }

        return user;
    }

    /**
     * 修改用户信息
     * @param username 用户名
     * @param signature 用户签名
     * @return
     */
    @RequestMapping(value = "/user/setUserMessage.do")
    @ResponseBody
    public Map<String,Object> setUserMessage(String username,String signature,String email){
        if(userService.setUserMessage(username,signature,email)){
            return ResponseUtil.getsuccessResponse();
        }else {
            return ResponseUtil.getFailureResponse("用户邮箱为空!");
        }
    }

    /**
     * 用户签到接口
     * @param email 用户邮箱
     * @return 返回是否签到成功
     */
    @RequestMapping(value = "/user/signIn.do")
    @ResponseBody
    public Map signIn(String email){
        if(userService.signIn(email)){
            return ResponseUtil.getsuccessResponse();
        }else {
            return ResponseUtil.getFailureResponse("签到失败！用户不存在或者用户已经签到！");
        }
    }


}
