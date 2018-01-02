package com.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
public class MainController {
    @RequestMapping(value = "/test.do",method = RequestMethod.GET)
    @ResponseBody
    public Object index(String name, HttpServletResponse response){
        List<String> list=new ArrayList<String>();
        list.add("123");
        list.add("cs");
        list.add("mywrod");

        HashMap map=new HashMap();
        map.put("ab",list);
        map.put("cd","sa");
        map.put("name",name);
        System.out.println(name);

        Cookie cookie=new Cookie("v","test");
        cookie.setPath("/");
        response.addCookie(cookie);
        
        return map;
    }
}
