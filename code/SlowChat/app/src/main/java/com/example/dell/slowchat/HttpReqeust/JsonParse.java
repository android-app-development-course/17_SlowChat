package com.example.dell.slowchat.HttpReqeust;

import com.example.dell.slowchat.ChatManage.ChatRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dell on 2017/12/27.
 */

public class JsonParse {
    public static List<ChatRecord> getChatRecords(String json){
        Gson gson=new Gson();
        Type listType=new TypeToken<List<TestData>>(){}.getType();
        List<ChatRecord> chatRecords=gson.fromJson(json,listType);
        return chatRecords;
    }

    public static SendResult getSendResult(String json){
        Gson gson=new Gson();
        Type type = new TypeToken<TestData>() {}.getType();
        SendResult sendResult = gson.fromJson(json, type);
        return sendResult;
    }

}
