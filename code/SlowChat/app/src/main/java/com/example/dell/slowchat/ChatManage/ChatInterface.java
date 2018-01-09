package com.example.dell.slowchat.ChatManage;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.slowchat.FriendManage.FriendInfoActivity;
import com.example.dell.slowchat.HttpReqeust.HttpHelper;
import com.example.dell.slowchat.HttpReqeust.JsonParse;
import com.example.dell.slowchat.MainInterface.MainActivity;
import com.example.dell.slowchat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


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
        setContentView(R.layout.chat_manage_chat_activity);
        this.setTitle(getFriendName());
        friendId=getFriendID();
        lastSendDate=getLastSendDate();
        ifFirstSend=true;
        inputBox=(EditText)findViewById(R.id.chat_manage_chat_input);
        initToolbar();
        initSQLOp();
        initPortrait();
        initChatMsgs(friendId);
        initListView();
        initSendBtn();
        getMsgFromServerEverySecond();
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_manage_chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_manage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case  R.id.chat_manage_action_info:
                //点击好友进入好友信息界面
                turnToFirendInfo();
                break;
            case android.R.id.home:
                returnToChatList();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void turnToFirendInfo(){
        Intent intent = new Intent(ChatInterface.this, FriendInfoActivity.class);
        int friendID=friendId;
        intent.putExtra("friend_id",friendID);
        String friendName=getFriendName();
        intent.putExtra("name",friendName);
        startActivity(intent);
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
                if(ifLegalString(inputString))
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


    private void dealSendMsg(String inputString){
        //构造输入消息
        ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeSend,inputString);
        chatMsgs.add(Message);
        if(sendMsgToServer(inputString))
            addChatInfosIntoSQLite(friendId,ChatMsg.MessageTypeSend,inputString);
        //更新数据
        mAdapter.Refresh();
    }


    private boolean sendMsgToServer(String message){
        RequestParams params=sendMsgToServerGetParams(message);
        try{
            HttpHelper.sendMsgToServer(this,params,getString(R.string.chat_manage_send_mag_server_url));
            return true;
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private RequestParams sendMsgToServerGetParams(String message){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("user_id",getUserId());
        hashMap.put("friend_id",String.valueOf(friendId));
        hashMap.put("send_msg",message);
        return HttpHelper.getParams(hashMap);
    }

    private String getUserId(){
        SharedPreferences sp = getSharedPreferences("SlowChat", Context.MODE_PRIVATE);
        return sp.getString("userId", null);
    }


    private boolean ifLegalString(String inputString){
        if(inputString.equals(""))
            return false;
        if(inputString.length()>1000){
            stringLenOutOfRange("输入字数不应超过1000字符！");
            return false;
        }
        return true;
    }

    private void stringLenOutOfRange(String tip){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(tip)
                .setPositiveButton("确认" ,null)
                .show();
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




    private void addChatInfosIntoSQLite(int friend_id,int type,String content){
        ContentValues values=new ContentValues();
        values.put("friend_id",String.valueOf(friend_id));
        values.put("msg_type",String.valueOf(type));
        values.put("content",content);
        writeDB.insert("message",null,values);
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


//    private void initChatMsgs(){
//        if(ifDBIsNull())
//            addDataIntoDB();
//        else
//            initChatMsgs(friendId);
//    }



//    private boolean ifDBIsNull(){
////        String delSql="delete from message";
////        writeDB.execSQL(delSql);
//        String[] columns = {"msg_type"};
//        String whereClause = "friend_id=?";
//        String[] whereArgs = {String.valueOf(friendId)};
//        Cursor cursor = readDB.query("message", columns, whereClause, whereArgs, null, null, null);
//        return !cursor.moveToFirst();
//    }






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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            returnToChatList();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void returnToChatList(){
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("content",chatMsgs.get(chatMsgs.size()-1).getContent());
        intent.putExtra("friend_id",friendId);
        if(ifFromMainActivity()) {
            setResult(1,intent);
            finish();
        }else{
            startActivity(intent);
            finish();
        }
    }

    private boolean ifFromMainActivity(){
        Intent intent=getIntent();
        int code=intent.getIntExtra("from_main",0);
        return code==1;
    }


    private void getMsgFromServerEverySecond(){
        handler.postDelayed(runnable, TIME); //每隔1s执行
    }

    Handler handler = new Handler();
    private int TIME = 1000;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                getMsgFromServer();
                mAdapter.Refresh();
                scrollMyListViewToBottom();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };


    private void getMsgFromServer(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(5);//5s超时
        RequestParams params=getMsgFromServerGetParams();
        client.post(getString(R.string.chat_manage_chat_record_server_url),params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers,
                                  byte[] bytes) {
                try {
                    String json=new String(bytes,"utf-8");
                    List<ChatRecord> chatRecords= JsonParse.getChatRecords(json);
                    if (chatRecords!=null)
                        for (ChatRecord chatRecord:chatRecords){
                            ChatMsg chatMsg=new ChatMsg(ChatMsg.MessageTypeGet,chatRecord.getContent());
                            chatMsgs.add(chatMsg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
//                Toast.makeText(ChatInterface.this,"网络请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private RequestParams getMsgFromServerGetParams(){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("user_id",getUserId());
        hashMap.put("friend_id",String.valueOf(friendId));
        return HttpHelper.getParams(hashMap);
    }


}
