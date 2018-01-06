package com.example.dell.slowchat.Login;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by you on 2018/1/3.
 */

public class JsonParse {


    //解析注册和登录的json
    public int getRegisterResult(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code");
            return code;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //解析返回个人信息的json
    public UserInfo getUserInfo(String json)
    {
        UserInfo userInfo = new UserInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            userInfo.setUserId(jsonObject.optInt("id"));
            userInfo.setUserEmail(jsonObject.optString("email"));
            userInfo.setUserSignature(jsonObject.optString("signature"));
            userInfo.setUserIntegral(jsonObject.optInt("integral"));
            userInfo.setUserStatus(jsonObject.optInt("status"));
            userInfo.setUserName(jsonObject.optString("username"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
