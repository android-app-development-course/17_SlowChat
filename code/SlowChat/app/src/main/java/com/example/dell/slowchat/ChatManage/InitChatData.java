package com.example.dell.slowchat.ChatManage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/12/31.
 */

public class InitChatData {

    SQLiteDatabase writeDB;
    List<ChatMsg> chatMsgs;

    public InitChatData(SQLiteDatabase writeDB){
        this.writeDB=writeDB;
        chatMsgs=loadChatMsgs();
    }


    public void addChatMsgsIntoDB(int friendId){
        for (ChatMsg chatMsg:chatMsgs){
            addChatInfosIntoSQLite(friendId,chatMsg.getType(),chatMsg.getContent());
        }
    }

    private List<ChatMsg> loadChatMsgs()
    {
        List<ChatMsg> chatMsgs=new ArrayList<>();

        ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-25");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"山重水复疑无路");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"柳暗花明又一村");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"青青子衿，悠悠我心");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"但为君故，沉吟至今");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-27");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"这是你做的Android程序吗？");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"是的，这是一个仿微信的聊天界面");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"为什么下面的消息发送不了呢");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-28");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"呵呵，我会告诉你那是直接拿图片做的么");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"哦哦，呵呵，你又在偷懒了");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"因为这一部分不是今天的重点啊");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-29");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"好吧，可是怎么发图片啊");
        chatMsgs.add(Message);

        return chatMsgs;
    }


    private void addChatInfosIntoSQLite(int friend_id,int type,String content){
        ContentValues values=new ContentValues();
        values.put("friend_id",String.valueOf(friend_id));
        values.put("msg_type",String.valueOf(type));
        values.put("content",content);
        writeDB.insert("message",null,values);
    }
}
