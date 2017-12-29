package com.example.dell.slowchat.ChatManage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dell.slowchat.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatInterface extends AppCompatActivity {

    private Button BtnSend;
    private EditText InputBox;
    private ArrayList<ChatMsg> chatMsgs;
    private ChatBaseAdapter mAdapter;
    private ListView mListView;

    private Drawable[] portraits;

    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_manage_chat);
        this.setTitle(getFriendName());
        initSQLOp();
        initPortrait();
        initChatMsgs();
        initListView();
        InputBox=(EditText)findViewById(R.id.chat_manage_chat_input);
        initSendBtn();
    }


    private String getFriendName(){
        Intent intent=getIntent();
        String friendName=intent.getStringExtra("name");
        return friendName;
    }


    private void initSQLOp(){
        SQLiteOp sqLiteOp=new SQLiteOp(this);
        writeDB=sqLiteOp.getWritableDatabase();
        readDB=sqLiteOp.getReadableDatabase();
    }


    public void initPortrait(){
        portraits=new Drawable[2];
        portraits[ChatMsg.MessageTypeGet]= getFriendPortrait();
        portraits[ChatMsg.MessageTypeSend]= ContextCompat.getDrawable(this,R.drawable.portrait_me);
    }


    private Drawable getFriendPortrait(){
        return ChatInfo.friendPortrait;
    }


    private void initSendBtn(){
        BtnSend=(Button)findViewById(R.id.chat_manage_chat_send);
        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                String inputString=InputBox.getText().toString();
                if(!inputString.equals(""))
                {
                    //获取时间
                    Calendar c=Calendar.getInstance();
                    StringBuilder mBuilder=new StringBuilder();
                    mBuilder.append(Integer.toString(c.get(Calendar.YEAR))+"年");
                    mBuilder.append(Integer.toString(c.get(Calendar.MONTH))+"月");
                    mBuilder.append(Integer.toString(c.get(Calendar.DATE))+"日");
                    mBuilder.append(Integer.toString(c.get(Calendar.HOUR_OF_DAY))+":");
                    mBuilder.append(Integer.toString(c.get(Calendar.MINUTE)));
                    //构造时间消息
                    ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeTime,mBuilder.toString());
                    chatMsgs.add(Message);
                    //构造输入消息
                    Message=new ChatMsg(ChatMsg.MessageTypeSend,inputString);
                    chatMsgs.add(Message);
                    //更新数据
                    mAdapter.Refresh();
                }
                //清空输入框
                InputBox.setText("");
                //关闭输入法
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
                //滚动列表到当前消息
                mListView.smoothScrollToPositionFromTop(chatMsgs.size(), 0);
            }
        });
    }




    private void initListView(){
        mListView=(ListView)findViewById(R.id.chat_manage_chat_msg_list);
        mAdapter=new ChatBaseAdapter(this,portraits,chatMsgs);
        mListView.setAdapter(mAdapter);
        mListView.smoothScrollToPositionFromTop(chatMsgs.size(), 0);
    }


    private void initChatMsgs(){
        if(ifDBIsNull())
            addDataIntoDB();
        else
            initChatMsgs(getFriendID());
    }


    private int getFriendID(){
        Intent intent=getIntent();
        int id=intent.getIntExtra("friend_id",1);
        return id;
    }




    private boolean ifDBIsNull(){
//        String delSql="delete from message";
//        writeDB.execSQL(delSql);
        String[] columns = {"msg_type"};
        String whereClause = "friend_id=?";
        String[] whereArgs = {String.valueOf(getFriendID())};
        Cursor cursor = readDB.query("message", columns, whereClause, whereArgs, null, null, null);
        return !cursor.moveToFirst();
    }


    private void addDataIntoDB(){
        loadData();
        for (ChatMsg chatMsg:chatMsgs){
            addChatInfosIntoSQLite(getFriendID(),chatMsg.getType(),chatMsg.getContent());
        }
    }

    private void loadData()
    {
        chatMsgs=new ArrayList<>();

        ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-27");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"山重水复疑无路");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"柳暗花明又一村");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"青青子衿，悠悠我心");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"但为君故，沉吟至今");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-29");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"这是你做的Android程序吗？");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"是的，这是一个仿微信的聊天界面");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"为什么下面的消息发送不了呢");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-30");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"呵呵，我会告诉你那是直接拿图片做的么");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"哦哦，呵呵，你又在偷懒了");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"因为这一部分不是今天的重点啊");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeTime,"2017-12-31");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"好吧，可是怎么发图片啊");
        chatMsgs.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"很简单啊，你继续定义一种布局类型，然后再写一个布局就可以了");
        chatMsgs.add(Message);

    }





    private void addChatInfosIntoSQLite(int friend_id,int type,String content){
        ContentValues values=new ContentValues();
        values.put("friend_id",String.valueOf(friend_id));
        values.put("msg_type",String.valueOf(type));
        values.put("content",content);
        writeDB.insert("message",null,values);
    }



    private void initChatMsgs(int friend_id){
        chatMsgs=new ArrayList<>();
        getChatInfoFromDB(friend_id);
    }




    private void getChatInfoFromDB(int friend_id) {
        String[] columns = {"msg_type", "content"};
        String whereClause = "friend_id=?";
        String[] whereArgs = {String.valueOf(friend_id)};
        Cursor cursor = readDB.query("message", columns, whereClause, whereArgs, null, null, null);
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int msg_type=cursor.getInt(0);
                String content = cursor.getString(1);
                ChatMsg chatMsg=new ChatMsg(msg_type,content);
                chatMsgs.add(chatMsg);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }




}
