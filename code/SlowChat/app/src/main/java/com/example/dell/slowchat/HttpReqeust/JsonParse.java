package com.example.dell.slowchat.HttpReqeust;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by dell on 2017/12/27.
 */

public class JsonParse {
//    public static List<TestData> getTestData(String json){
//        Gson gson=new Gson();
//        Type listType=new TypeToken<List<TestData>>(){}.getType();
//        List<TestData> testDatas=gson.fromJson(json,listType);
//        return testDatas;
//    }

    public static TestData getTestData(String json){
        Gson gson=new Gson();
        Type type = new TypeToken<TestData>() {}.getType();
        TestData testData = gson.fromJson(json, type);
        return testData;
    }
}
