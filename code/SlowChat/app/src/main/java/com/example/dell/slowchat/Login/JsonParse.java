package com.example.dell.slowchat.Login;

import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by you on 2018/1/3.
 */

public class JsonParse {


    //解析注册和登录的json
    public int getRegisterResult(byte bytes[])
    {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        try
        {
            jsonReader.beginObject();
            if(jsonReader.hasNext())
            {
                String keyName = jsonReader.nextName();
                if("code".equals(keyName))
                {
                    int code = jsonReader.nextInt();
                    return code;
                }

            }
            jsonReader.endObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    //解析返回个人信息的json
    public UserInfo getUserInfo(byte bytes[])
    {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        UserInfo userInfo = new UserInfo();
        try
        {
            jsonReader.beginObject();
           while (jsonReader.hasNext())
            {
                String keyName = jsonReader.nextName();
                if("id".equals(keyName))
                {
                    userInfo.setUserId(jsonReader.nextInt());
                }
                else if("email".equals(keyName))
                {
                    userInfo.setUserEmail(jsonReader.nextString());
                }
                else if("signature".equals(keyName)) {
                    userInfo.setUserSignature(jsonReader.nextString());
                }
                else if("integral".equals(keyName))
                {
                    userInfo.setUserIntegral(jsonReader.nextInt());
                }
                else if("status".equals(keyName))
                {
                    userInfo.setUserState(jsonReader.nextInt());
                }
                else if("img".equals(keyName))
                {
                    userInfo.setUserImage(Drawable.createFromPath(jsonReader.nextString()));
                }
                else if("username".equals(keyName))
                {
                    userInfo.setUserName(jsonReader.nextString());
                }

            }
            jsonReader.endObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }
}
