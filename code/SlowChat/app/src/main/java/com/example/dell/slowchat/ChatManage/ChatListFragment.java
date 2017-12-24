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
import android.widget.TextView;

import com.example.dell.slowchat.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

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
        getFriendInfoFromDB();
    }


    private void getFriendInfoFromDB() {
        String[] columns = {"friend_id","name", "portrait"};
        Cursor cursor = readDB.query("friend", columns, null, null, null, null, null);
        String[] talks={"你好","还好吗","昨天多谢了","有啥事啊","what"};
        String[] time={"12月4号","12月4号","12月1号","11月27号","11月30号"};
        int[] msgNum={1,2,0,4,6};
        int index=0;
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] portrait = cursor.getBlob(2);
                Drawable dPortrait=exchangeByteToDrawble(portrait);
                ChatInfo chatInf=new ChatInfo(id,name,time[index],talks[index],msgNum[index],dPortrait);
                this.chatInfos.add(chatInf);
                index++;
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private Drawable exchangeByteToDrawble(byte[] blob){
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        BitmapDrawable bd = new BitmapDrawable(this.getResources(),bmp);
        return bd;
    }


//    private void addAllFriends(){
//        for (int i=0;i<this.chatInfos.size();i++) {
//            addFriendInfoIntoSQLite(chatInfos.get(i).getName(),i+1);
//        }
//    }

    private Drawable getPortrait(int position){
        String pictureName="portrait"+String.valueOf(position);
        int picID=getPictureID(pictureName);
        if (picID!=0) {
            return ContextCompat.getDrawable(getContext(),picID);
        }
        return null;
    }


    private void addFriendInfoIntoSQLite(String name,int position){
        Drawable portrait=getPortrait(position);
        addFriendInfoIntoSQLite(name,drawableToBytes(portrait));
    }


    private byte[] drawableToBytes(Drawable picture){
        Bitmap bmp = (((BitmapDrawable)picture).getBitmap());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }


    private void addFriendInfoIntoSQLite(String name,byte[] portrait){
        ContentValues values=new ContentValues();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        writeDB.close();
    }
}
