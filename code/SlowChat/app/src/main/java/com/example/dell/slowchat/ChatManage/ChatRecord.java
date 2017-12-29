package com.example.dell.slowchat.ChatManage;

/**
 * Created by dell on 2017/12/27.
 */



public class ChatRecord {
    private int friendId;
    private String time;
    private String content;

    public ChatRecord(int friendId, String time, String content) {
        this.friendId=friendId;
        this.time=time;
        this.content = content;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
