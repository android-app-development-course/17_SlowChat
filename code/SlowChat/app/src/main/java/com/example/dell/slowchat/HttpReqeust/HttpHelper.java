package com.example.dell.slowchat.HttpReqeust;

import android.content.Context;
import android.widget.Toast;

import com.example.dell.slowchat.ChatManage.ChatInterface;
import com.example.dell.slowchat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

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


    public static void sendMsgToServer(final Context context, RequestParams params, final String sendMsgUrl){
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(5);//5s超时
        client.post(sendMsgUrl,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers,
                                  byte[] bytes) {
                try {
                    String json=new String(bytes,"utf-8");
                    SendResult sendResult= JsonParse.getSendResult(json);
                    if (sendResult==null)
                        Toast.makeText(context,"解析失败",Toast.LENGTH_SHORT).show();
                    if(sendResult.getCode()!=200)
                        throw new RuntimeException(sendResult.getMsg());
                }catch (Exception e){
                    Toast.makeText(context,"解析失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(context,"无法连接服务器",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
