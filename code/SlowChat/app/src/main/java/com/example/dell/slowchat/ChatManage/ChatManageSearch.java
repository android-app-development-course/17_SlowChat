package com.example.dell.slowchat.ChatManage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.ListView;

import com.example.dell.slowchat.R;

import java.util.ArrayList;

public class ChatManageSearch extends AppCompatActivity
{
    private SearchView mSearchView;
    private ListView mListView;
    ChatSearchAdapter chatSearchAdapter;
    private SQLiteDatabase readDB;
    private ArrayList<ChatInfo> allChatInfos;
    private ArrayList<ChatInfo> showChatInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_manage_search);
        setTitle(getString(R.string.chat_manage_chat_search));
        readDB=new SQLiteOp(this).getReadableDatabase();
        initSearchView();
        initListData();
        initListView();
    }



    private void initListData()
    {
        allChatInfos=new ArrayList<>();
        showChatInfos=new ArrayList<>();
        getFriendInfoFromDB();
    }



    private void initListView()
    {
        mListView = (ListView) findViewById(R.id.chat_manage_search_list);
        chatSearchAdapter=new ChatSearchAdapter(this,showChatInfos);
        mListView.setAdapter(chatSearchAdapter);
        initListViewClickAction();
    }

    private void initListViewClickAction(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ChatManageSearch.this,ChatInterface.class);
                int friendID=showChatInfos.get(position).getFriendId();
                intent.putExtra("friend_id",friendID);
                String friendName=showChatInfos.get(position).getName();
                intent.putExtra("name",friendName);
                setFriendPortrait(position);
                startActivity(intent);
            }
        });
    }

    private void setFriendPortrait(int position){
        Drawable portrait=showChatInfos.get(position).getPortrait();
        ChatInfo.friendPortrait=portrait;
    }


    private void initSearchView(){
        mSearchView = (SearchView) findViewById(R.id.chat_manage_searchView);
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                setShowChatInfos(query);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                setShowChatInfos(newText);
                return false;
            }
        });
    }


    private void setShowChatInfos(String newText){
        showChatInfos=new ArrayList<>();
        if (!TextUtils.isEmpty(newText)){
            subSetShowChatInfos(newText);
        }
        chatSearchAdapter=new ChatSearchAdapter(ChatManageSearch.this,showChatInfos);
        mListView.setAdapter(chatSearchAdapter);
    }

    private void subSetShowChatInfos(String newText){
        for (ChatInfo chatInfo:allChatInfos){
            if(chatInfo.getName().contains(newText)&&!showChatInfos.contains(chatInfo))
                showChatInfos.add(chatInfo);
        }
    }


    private void getFriendInfoFromDB()
    {
        String[] columns = {"friend_id","name", "portrait"};
        Cursor cursor = readDB.query("friend", columns, null, null, null, null, null);
//        String[] talks={"你好","还好吗","昨天多谢了","有啥事啊","what"};
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] portrait = cursor.getBlob(2);
                Drawable dPortrait=exchangeByteToDrawble(portrait);
                ChatInfo chatInf=new ChatInfo(id,name,dPortrait);
                this.allChatInfos.add(chatInf);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private Drawable exchangeByteToDrawble(byte[] blob)
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        BitmapDrawable bd = new BitmapDrawable(this.getResources(),bmp);
        return bd;
    }

}
