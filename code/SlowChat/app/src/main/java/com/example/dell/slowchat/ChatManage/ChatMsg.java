package com.example.dell.slowchat.ChatManage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.dell.slowchat.R;

/**
 * Created by dell on 2017/12/16.
 */

public class ChatMsg {
    //定义3种布局类型
    public static final int MessageTypeGet=0;
    public static final int MessageTypeSend=1;
    public static final int MessageTypeTime=2;



    public ChatMsg(int Type,String Content)
    {
        this.mType=Type;
        this.mContent=Content;
    }


    //消息类型
    private int mType;
    //消息内容
    private String mContent;
    //获取类型
    public int getType() {
        return mType;
    }
    //设置类型
    public void setType(int mType) {
        this.mType = mType;
    }
    //获取内容
    public String getContent() {
        return mContent;
    }
    //设置内容
    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
