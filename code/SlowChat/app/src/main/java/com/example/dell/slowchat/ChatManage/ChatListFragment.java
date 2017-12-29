package com.example.dell.slowchat.ChatManage;

/**
 * Created by dell on 2017/11/28.
 */


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dell.slowchat.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatListFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<ChatInfo> chatInfos;
    private ListView chatList;
    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;

    private HashMap<Integer,Integer> chatMsgNum;


    public ChatListFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChatListFragment newInstance(int sectionNumber)
    {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.chat_manage_main, container, false);
        this.initSQLOp();
        this.addNewChatMsgIntoDB();
        this.iniChatList(rootView);
        return rootView;
    }


    private void initSQLOp(){
        SQLiteOp sqLiteOp=new SQLiteOp(getContext());
        writeDB=sqLiteOp.getWritableDatabase();
        readDB=sqLiteOp.getReadableDatabase();
    }



    private void iniChatList(View rootView ){
        initChatInfos();
        this.chatList=(ListView)rootView.findViewById(R.id.chat_manage_chat_list);
        MyBaseAdapter myBaseAdapter=new MyBaseAdapter(getContext(),this.chatInfos);
        this.chatList.setAdapter(myBaseAdapter);
        initListViewClickAction();
    }


    private void initListViewClickAction(){
        this.chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),ChatInterface.class);
                int friendID=chatInfos.get(position).getFriendId();
                intent.putExtra("friend_id",friendID);
                String friendName=chatInfos.get(position).getName();
                intent.putExtra("name",friendName);
                setFriendPortrait(position);
                startActivity(intent);
            }
        });
    }


    private void setFriendPortrait(int position){
        Drawable portrait=chatInfos.get(position).getPortrait();
        ChatInfo.friendPortrait=portrait;
    }


    private void initChatInfos(){
        this.chatInfos=new ArrayList<>();
        if(ifDBIsNull())
            addAllFriends();
        else
            getFriendInfoFromDB();
    }


    private void getFriendInfoFromDB() {
        String[] columns = {"friend_id","name", "portrait"};
        Cursor cursor = readDB.query("friend", columns, null, null, null, null, null);
//        String[] talks={"你好","还好吗","昨天多谢了","有啥事啊","what"};
        HashMap<Integer,String> lastMsg=getLastMsg();
        HashMap<Integer,String> lastSendTime=getLastSendTime();
        int index=0;
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] portrait = cursor.getBlob(2);
                Drawable dPortrait=exchangeByteToDrawble(portrait);
                String lastSenTime=(new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
                if(lastSendTime.containsKey(id))
                    lastSenTime=lastSendTime.get(id);
                String talk="你们已是朋友了，赶紧开始聊天吧！";
                if(lastMsg.containsKey(id))
                    talk=lastMsg.get(id);
                int msgNum=0;
                if(chatMsgNum.containsKey(id))
                    msgNum=chatMsgNum.get(id);
                ChatInfo chatInf=new ChatInfo(id,name,lastSenTime,talk,msgNum,dPortrait);
                this.chatInfos.add(chatInf);
                index++;
            }while (cursor.moveToNext());
        }
        cursor.close();
    }


    private HashMap<Integer,String> getLastSendTime(){
        HashMap<Integer,String> lastSendTime=new HashMap<>();
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
        return lastSendTime;
    }


    private HashMap<Integer,String> getLastMsg(){
        HashMap<Integer,String> LastMsg=new HashMap<>();
        String sqlCmm="SELECT message1.friend_id,message1.content \n" +
                "FROM message message1,(SELECT friend_id,MAX(id) AS max_id FROM message WHERE msg_type<>" +String .valueOf(ChatMsg.MessageTypeTime)+
                " GROUP BY friend_id) message2\n" +
                "WHERE message1.friend_id=message2.friend_id AND message1.id=message2.max_id;";
        Cursor cursor=readDB.rawQuery(sqlCmm,null);
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String time = cursor.getString(1);
                LastMsg.put(id,time);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return LastMsg;
    }

    private Drawable exchangeByteToDrawble(byte[] blob){
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        BitmapDrawable bd = new BitmapDrawable(this.getResources(),bmp);
        return bd;
    }


    private boolean ifDBIsNull(){
        String command="select count(*) from friend";
        Cursor cursor =readDB.rawQuery(command,null);
        cursor.moveToFirst();
        int dataNum=cursor.getInt(0);
        cursor.close();
        return dataNum==0;
    }

    private void addAllFriends(){
        loadDataForChatInfos();
        for (int i=0;i<this.chatInfos.size();i++) {
            addFriendInfoIntoSQLite(chatInfos.get(i).getFriendId(),chatInfos.get(i).getName(),i+1);
        }
    }

    private void loadDataForChatInfos(){
        ChatInfo chatInfo;
        chatInfo=new ChatInfo(1,"张三","12月4号","你好",1,getPortrait(1));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(2,"李四","12月4号","还好吗",2,getPortrait(2));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(3,"王五","12月1号","昨天多谢了",0,getPortrait(3));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(4,"小明","11月25号","有啥事啊",4,getPortrait(4));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(5,"小红","11月20号","what",0,getPortrait(5));
        chatInfos.add(chatInfo);
    }


    private Drawable getPortrait(int position){
        String pictureName="portrait"+String.valueOf(position);
        int picID=getPictureID(pictureName);
        if (picID!=0) {
            return ContextCompat.getDrawable(getContext(),picID);
        }
        return null;
    }


    private void addFriendInfoIntoSQLite(int friendId,String name,int position){
        Drawable portrait=getPortrait(position);
        addFriendInfoIntoSQLite(friendId,name,drawableToBytes(portrait));
    }


    private byte[] drawableToBytes(Drawable picture){
        Bitmap bmp = (((BitmapDrawable)picture).getBitmap());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }


    private void addFriendInfoIntoSQLite(int friendId,String name,byte[] portrait){
        ContentValues values=new ContentValues();
        values.put("friend_id",friendId);
        values.put("name",name);
        values.put("portrait",portrait);
        writeDB.insert("friend",null,values);
    }

    public int getPictureID(String pictureName){
        Class drawable = R.drawable.class;
        Field field;
        try {
            field = drawable.getField(pictureName);
            int res_ID = field.getInt(field.getName());
            return res_ID;
        } catch (Exception e) {
            return 0;
        }

    }


    private void addNewChatMsgIntoDB(){
        List<ChatRecord> chatRecords=getDataFromServer();
        initChatNum(chatRecords);
        DealReceiveMsg dealReceiveMsg=new DealReceiveMsg(chatRecords,readDB);
        dealReceiveMsg.writeDataIntoDB(writeDB);
    }


    private void initChatNum(List<ChatRecord> chatRecords){
        chatMsgNum=new HashMap<>();
        for(ChatRecord chatRecord:chatRecords){
            int friendId=chatRecord.getFriendId();
            if(chatMsgNum.containsKey(friendId))
                chatMsgNum.put(friendId,chatMsgNum.get(friendId)+1);
            else
                chatMsgNum.put(friendId,1);
        }
    }

    private List<ChatRecord> getDataFromServer(){
        List<ChatRecord> chatRecords =new ArrayList<>();
//        for (int i=1;i<5;i++) {
//            ChatRecord rercord = new ChatRecord(i, "2017-12-27", "就你会吹比");
//            chatRecords.add(rercord);
//            rercord = new ChatRecord(i, "2017-12-27", "不吹会死啊");
//            chatRecords.add(rercord);
//        }
        return chatRecords;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        writeDB.close();
    }
}
