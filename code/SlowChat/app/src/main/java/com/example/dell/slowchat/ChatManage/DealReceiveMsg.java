package com.example.dell.slowchat.ChatManage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/12/28.
 */

public class DealReceiveMsg {
    private HashMap<Integer,TimeContent> chatRecords;

    private HashMap<Integer,String> lastSendTime;

    public DealReceiveMsg(List<ChatRecord> chatRecords,SQLiteDatabase readDB){
        initLastSendTime(readDB);
        initChatRecords(chatRecords);
    }


    private void initChatRecords(List<ChatRecord> chatRecords){
        this.chatRecords=new HashMap<>();
        for (ChatRecord chatRecord: chatRecords) {
            int friendId=chatRecord.getFriendId();
            String time=chatRecord.getTime();
            String content=chatRecord.getContent();
            if(!this.chatRecords.containsKey(friendId)){
                TimeContent timeContent=new TimeContent(time,content);
                this.chatRecords.put(friendId,timeContent);
            }else {
                this.chatRecords.get(friendId).put(time,content);
            }
        }
    }

    private void initLastSendTime(SQLiteDatabase readDB){
        lastSendTime=new HashMap<>();
        String sqlCmm="SELECT message1.friend_id,message1.content \n" +
                "FROM message message1,(SELECT friend_id,MAX(id) AS max_id FROM message WHERE msg_type=" +String .valueOf(ChatMsg.MessageTypeTime)+
                " GROUP BY friend_id) message2\n" +
                "WHERE message1.friend_id=message2.friend_id AND message1.id=message2.max_id;";
        Cursor cursor=readDB.rawQuery(sqlCmm,null);
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String time = cursor.getString(1);
                lastSendTime.put(id,time);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void writeDataIntoDB(SQLiteDatabase writeDB){
        for (Map.Entry<Integer,TimeContent> entry:chatRecords.entrySet()) {
            int friendId=entry.getKey();
            TimeContent timeContent=entry.getValue();
            timeContent.writeDataIntoDB(writeDB,lastSendTime, friendId);
        }
    }


}


class TimeContent{
    private HashMap<String,List<String>> timeContentMap;

    public TimeContent(String time,String content){
        List<String> contents=new ArrayList<>();
        contents.add(content);
        timeContentMap=new HashMap<>();
        timeContentMap.put(time,contents);
    }

    public  void put(String time,String content){
        if (timeContentMap.containsKey(time))
            timeContentMap.get(time).add(content);
        else{
            List<String> contents=new ArrayList<>();
            contents.add(content);
            timeContentMap.put(time,contents);
        }
    }

    public void writeDataIntoDB(SQLiteDatabase writeDB,HashMap<Integer,String> lastSendTimes,int friendId){
        for (Map.Entry<String,List<String>> entry:timeContentMap.entrySet()) {
            String time=entry.getKey();
            List<String> contents=entry.getValue();
            if(lastSendTimes.containsKey(friendId)){
                toAddTime(writeDB,lastSendTimes,friendId,time);
            }else {
                addChatInfosIntoSQLite(writeDB,friendId,ChatMsg.MessageTypeTime,time);
            }
            addContents(writeDB,contents,friendId);
        }
    }

    private void toAddTime(SQLiteDatabase writeDB,HashMap<Integer,String> lastSendTimes,int friendId,String time){
        String lastSendTime=lastSendTimes.get(friendId);
        if(compareTwoTime(time,lastSendTime))
            addChatInfosIntoSQLite(writeDB,friendId,ChatMsg.MessageTypeTime,time);
    }



    private boolean compareTwoTime(String time1,String time2){
        try {
            Calendar calendar1=getCalendar(time1);
            Calendar calendar2=getCalendar(time2);
            return calendar1.compareTo(calendar2)>0;
        }catch (StackOverflowError e){
            e.printStackTrace();
        }
        return false;
    }


    private Calendar getCalendar(String time){
        Calendar calendar = Calendar.getInstance();
        String[] times = time.split("-");
        calendar.set(Integer.valueOf(times[0]), Integer.valueOf(times[1]), Integer.valueOf(times[2]));
        return calendar;
    }


    private void addContents(SQLiteDatabase writeDB,List<String> contents,int friendId){
        for (String content:contents) {
            addChatInfosIntoSQLite(writeDB,friendId,ChatMsg.MessageTypeGet,content);
        }
    }


    private void addChatInfosIntoSQLite(SQLiteDatabase writeDB,int friend_id,int type,String content){
        ContentValues values=new ContentValues();
        values.put("friend_id",String.valueOf(friend_id));
        values.put("msg_type",String.valueOf(type));
        values.put("content",content);
        writeDB.insert("message",null,values);
    }
}
