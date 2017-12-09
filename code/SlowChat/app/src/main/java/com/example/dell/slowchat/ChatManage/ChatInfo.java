package com.example.dell.slowchat.ChatManage;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by dell on 2017/12/9.
 */

public class ChatInfo {
    private int friendId;
    private String name;
    private String time;
    private String content;
    private int msgNum;
    private Drawable portrait;

    public ChatInfo(int friendId,String name,String time,String content,int msgNum,Drawable portrait){
        this.friendId=friendId;
        this.name=name;
        this.time=time;
        this.content=content;
        this.msgNum=msgNum;
        this.portrait=portrait;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getMsgNum() {
        return msgNum;
    }

    public int getFriendId() {
        return friendId;
    }

    public Drawable getPortrait() {
        return portrait;
    }
}
