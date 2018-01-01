package com.example.dell.slowchat.HttpReqeust;

import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/12/30.
 */

public class HttpHelper {

    public static RequestParams getParams(HashMap<String,String> sendData){
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        for (Map.Entry entry:sendData.entrySet()) {
            params.put((String) entry.getKey(),(String)entry.getValue());
        }
        return params;
    }
}
