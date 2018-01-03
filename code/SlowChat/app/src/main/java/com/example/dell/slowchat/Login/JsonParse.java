package com.example.dell.slowchat.Login;

import android.content.ContentValues;
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

    public static List<String> getInfo(String json)
    {
        //使用Gson库解析JSON数据
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>(){}.getType();
        //把获得的信息集合存到Infos中
        List<String> Infos = gson.fromJson(json, listType);
        return Infos;
    }
}
