package com.chat.application;

import com.chat.util.SpringUtil;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 返回操作失败的response哈希表模板
     * @return 失败时候的哈希表模板
     */
    public static Map<String,Object> getFailureResponse(String msg){
        Map<String,Object> map=SpringUtil.getBean("failureResponse",Response.class).getMap();
        map.put("msg",msg);
        return map;
    }

    /**
     * 获取成功操作的模板
     * @return 返回成功操作时的哈希表
     */
    public static Map<String,Object> getsuccessResponse(){
        return SpringUtil.getBean("successResponse",Response.class).getMap();
    }

    /**
     * 获取成功操作的模板
     * @param msg 要发送的消息
     * @return 返回成功操作的哈希表
     */
    public static Map<String,Object> getsuccessResponse(String msg){
        Map<String,Object> map=getsuccessResponse();
        map.put("msg",msg);
        return map;
    }

}
