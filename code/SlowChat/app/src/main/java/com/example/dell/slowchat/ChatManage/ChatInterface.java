package com.example.dell.slowchat.ChatManage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dell.slowchat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatInterface extends AppCompatActivity {

    private Button BtnSend;
    private EditText inputBox;
    private ArrayList<ChatMsg> chatMsgs;
    private ChatBaseAdapter mAdapter;
    private ListView mListView;

    private Drawable[] portraits;

    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;

    private boolean ifFirstSend;

    private int friendId;
    private String lastSendDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_manage_chat);
        this.setTitle(getFriendName());
        friendId=getFriendID();
        lastSendDate=getLastSendDate();
        ifFirstSend=true;
        inputBox=(EditText)findViewById(R.id.chat_manage_chat_input);

        initSQLOp();
        initPortrait();
        initChatMsgs();
        initListView();
        initSendBtn();
    }

    private String getFriendName(){
        Intent intent=getIntent();
        String friendName=intent.getStringExtra("name");
        return friendName;
    }

    private int getFriendID(){
        Intent intent=getIntent();
        int id=intent.getIntExtra("friend_id",1);
        return id;
    }

    private String getLastSendDate(){
        Intent intent=getIntent();
        String lastSendDate=intent.getStringExtra("last_send_date");
        return lastSendDate;
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
                String inputString=inputBox.getText().toString();
                if(!inputString.equals(""))
                {
                    dealFirstSend();
                    dealSendMsg(inputString);
                }
                //清空输入框
                inputBox.setText("");
                //关闭输入法
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
                //滚动列表到当前消息
                mListView.smoothScrollToPositionFromTop(chatMsgs.size(), 0);
            }
        });
    }


    private void dealFirstSend(){
        if(ifFirstSend){
            String currentDate=getCurrentDate();
            if(lastSendDate==null||compareTwoTime(currentDate,lastSendDate)){
                lastSendDate=currentDate;
                addChatInfosIntoSQLite(friendId,ChatMsg.MessageTypeTime,currentDate);
                ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeTime,currentDate);
                chatMsgs.add(Message);
            }
        }
        ifFirstSend=false;
    }


    private String getCurrentDate(){
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
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


    private void dealSendMsg(String inputString){
        //构造输入消息
        ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeSend,inputString);
        chatMsgs.add(Message);
        addChatInfosIntoSQLite(friendId,ChatMsg.MessageTypeSend,inputString);
        //更新数据
        mAdapter.Refresh();
    }


    private void initListView(){
        mListView=(ListView)findViewById(R.id.chat_manage_chat_msg_list);
        mAdapter=new ChatBaseAdapter(this,portraits,chatMsgs);
        mListView.setAdapter(mAdapter);
        scrollMyListViewToBottom();
    }

    private void scrollMyListViewToBottom() {
        mListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
    }


    private void initChatMsgs(){
        if(ifDBIsNull())
            addDataIntoDB();
        else
            initChatMsgs(friendId);
    }



    private boolean ifDBIsNull(){
//        String delSql="delete from message";
//        writeDB.execSQL(delSql);
        String[] columns = {"msg_type"};
        String whereClause = "friend_id=?";
        String[] whereArgs = {String.valueOf(friendId)};
        Cursor cursor = readDB.query("message", columns, whereClause, whereArgs, null, null, null);
        return !cursor.moveToFirst();
    }


    private void addDataIntoDB(){
        loadData();
        for (ChatMsg chatMsg:chatMsgs){
            addChatInfosIntoSQLite(friendId,chatMsg.getType(),chatMsg.getContent());
        }
    }

    private void loadData()
    {
        chatMsgs=new ArrayList<>();

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
