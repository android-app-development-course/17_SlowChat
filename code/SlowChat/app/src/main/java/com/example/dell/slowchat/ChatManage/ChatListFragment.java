package com.example.dell.slowchat.ChatManage;

/**
 * Created by dell on 2017/11/28.
 */


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.slowchat.HttpReqeust.JsonParse;
import com.example.dell.slowchat.HttpReqeust.TestData;
import com.example.dell.slowchat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
    ChatMainListAdapter chatMainListAdapter;

    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;

    private HashMap<Integer,Integer> chatMsgNum;
    private HashMap<Integer,String> lastSendDate;
    private HashMap<Integer,String> lastMsg;


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
        initSQLOp();
        initShowDataInListView();
        iniChatList(rootView);
//        testGetDataFromServer();
        return rootView;
    }


    private void initSQLOp(){
        SQLiteOp sqLiteOp=new SQLiteOp(getContext());
        writeDB=sqLiteOp.getWritableDatabase();
        readDB=sqLiteOp.getReadableDatabase();
    }



    private void initShowDataInListView(){
        addNewChatMsgIntoDB();
        initLastMsg();
        initLastSendDate();
        initChatInfos();
        setTopFriend();
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
//            ChatRecord record = new ChatRecord(i, "2017-12-30", "就你会吹比");
//            chatRecords.add(record);
//            record = new ChatRecord(i, "2017-12-30", "不吹会死啊");
//            chatRecords.add(record);
//        }
        return chatRecords;
    }




    private void initLastSendDate(){
        lastSendDate=new HashMap<>();
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
                lastSendDate.put(id,time);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }


    private void initLastMsg(){
        lastMsg=new HashMap<>();
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
                lastMsg.put(id,time);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }



    private void initChatInfos(){
        this.chatInfos=new ArrayList<>();
        if(ifDBIsNull())
            initFriendData();
        else
            getFriendInfoFromDB();
    }

    private boolean ifDBIsNull(){
        String command="select count(*) from friend";
        Cursor cursor =readDB.rawQuery(command,null);
        cursor.moveToFirst();
        int dataNum=cursor.getInt(0);
        cursor.close();
        return dataNum==0;
    }


    private void getFriendInfoFromDB() {
        String[] columns = {"friend_id","name", "portrait"};
        Cursor cursor = readDB.query("friend", columns, null, null, null, null, null);
//        String[] talks={"你好","还好吗","昨天多谢了","有啥事啊","what"};
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String talk="";
                if(lastMsg.containsKey(id))//db中没有该好友的聊天信息，不显示该好友
                    talk=lastMsg.get(id);
                else
                    continue;
                String name = cursor.getString(1);
                byte[] portrait = cursor.getBlob(2);
                Drawable dPortrait=exchangeByteToDrawble(portrait);
                String lastSenTime=(new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
                if(lastSendDate.containsKey(id))
                    lastSenTime=lastSendDate.get(id);
                int msgNum=0;
                if(chatMsgNum.containsKey(id))
                    msgNum=chatMsgNum.get(id);
                ChatInfo chatInf=new ChatInfo(id,name,lastSenTime,talk,msgNum,dPortrait);
                this.chatInfos.add(chatInf);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }




    private Drawable exchangeByteToDrawble(byte[] blob){
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        BitmapDrawable bd = new BitmapDrawable(this.getResources(),bmp);
        return bd;
    }




    private void initFriendData(){
        loadDataForChatInfos();
        InitChatData initChatData=new InitChatData(writeDB);
        for (int i=0;i<this.chatInfos.size();i++) {
            int friendId=chatInfos.get(i).getFriendId();
            addFriendInfoIntoSQLite(friendId,chatInfos.get(i).getName(),i+1);
            initChatData.addChatMsgsIntoDB(friendId);
        }
    }


    private void loadDataForChatInfos(){
        ChatInfo chatInfo;
        chatInfo=new ChatInfo(1,"张三","2017-12-28","啥事",1,getPortrait(1));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(2,"李四","2017-12-28","你好",2,getPortrait(2));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(3,"王五","2017-12-25","好吧，可是怎么发图片啊",0,getPortrait(3));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(4,"小明","2017-12-26","what",4,getPortrait(4));
        chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(5,"小红","2017-12-27","你丫的",0,getPortrait(5));
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


    private void setTopFriend(){
        SharedPreferences sp = getContext().getSharedPreferences("chat_manage", Context.MODE_PRIVATE);
        int friendId = sp.getInt("top_friend_id", 0);
        for (ChatInfo chatInfo:chatInfos){
            if(chatInfo.getFriendId()==friendId){
                chatInfos.remove(chatInfo);
                chatInfos.add(0,chatInfo);
                break;
            }
        }
    }


    private void iniChatList(View rootView ){
        this.chatList=(ListView)rootView.findViewById(R.id.chat_manage_chat_list);
        chatMainListAdapter =new ChatMainListAdapter(getContext(),this.chatInfos);
        this.chatList.setAdapter(chatMainListAdapter);
        initListViewClickAction();
        onItemLongClick();
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
                String lastSendDate= ChatListFragment.this.lastSendDate.get(friendID);
                intent.putExtra("last_send_date",lastSendDate);
                setFriendPortrait(position);
                intent.putExtra("from_main",1);
                startActivityForResult(intent,1);
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            String content=data.getStringExtra("content");
            int friendId=data.getIntExtra("friend_id",0);
            resetListData(content,friendId);
        }
    }


    private void resetListData(String content, int friendId){
        if(lastMsg.containsKey(friendId)){
            lastMsg.put(friendId,content);
            for(ChatInfo chatInfo:chatInfos){
                if(chatInfo.getFriendId()==friendId){
                    chatInfo.setContent(content);
                    chatInfo.setMsgNum(0);
                    break;
                }
            }
        }
        chatMainListAdapter.refresh();
    }


    private void setFriendPortrait(int position){
        Drawable portrait=chatInfos.get(position).getPortrait();
        ChatInfo.friendPortrait=portrait;
    }


    private void onItemLongClick() {
//注：setOnCreateContextMenuListener是与下面onContextItemSelected配套使用的
        chatList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0,0,0,"删除");
                menu.add(0,1,0,"置顶");
            }
        });
    }


    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        int position = (int) info.id;// 这里的info.id对应的就是数据库中_id的值
        String friendName=chatInfos.get(position).getName();
        switch(item.getItemId()) {
            case 0:
                String deleteTip="你确定要删除好友 "+friendName+" 的聊天信息吗？";
                deleteDialogTip(deleteTip,position);
                break;
            case 1:
                String topTip="你确定要置顶好友 "+friendName+" 的聊天信息吗？";
                setTopDialogTip(topTip,position);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }



    private void deleteDialogTip(String tip,final int position){
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage(tip)
                .setPositiveButton("确认" ,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                deleteFriendMsg(position);
                            }
                        }
                )
                .setNegativeButton("取消" ,null)
                .show();
    }


    private void deleteFriendMsg(int position){
        deleteFriendInfosInDB(position);
        deleteFriendInfosInListView(position);
    }


    private void deleteFriendInfosInListView(int position){
        chatInfos.remove(position);
        chatMainListAdapter.refresh();
    }


    private void deleteFriendInfosInDB(int position){
        int friendId=chatInfos.get(position).getFriendId();
        deleteDataInDB(friendId);
    }

    private void deleteDataInDB(int friendId){
        String whereClause = "friend_id=?";
        String[] whereArgs = {String.valueOf(friendId)};
        writeDB.delete("message",whereClause,whereArgs);
    }


    private void setTopDialogTip(String tip,final int position){
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage(tip)
                .setPositiveButton("确认" ,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                recordTopFriend(position);
                                setTopFriendInfo(position);
                            }
                        }
                )
                .setNegativeButton("取消" ,null)
                .show();
    }

    private void recordTopFriend(int position){
        int friendId=chatInfos.get(position).getFriendId();
        SharedPreferences sp = getContext().getSharedPreferences("chat_manage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("top_friend_id", friendId);
        editor.apply();
    }


    private void setTopFriendInfo(int position){
        ChatInfo chatInfo=chatInfos.get(position);
        chatInfos.remove(position);
        chatInfos.add(0,chatInfo);
        chatMainListAdapter.refresh();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        writeDB.close();
    }

    private void testGetDataFromServer(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(5);//5s超时
        client.post(getString(R.string.server_url), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers,
                                  byte[] bytes) {
                try {
                    String json=new String(bytes,"utf-8");
                    TestData testData= JsonParse.getTestData(json);
                    if (testData==null)
                        Toast.makeText(getContext(),"解析失败",Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getContext(),testData.getCities().toString(),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
