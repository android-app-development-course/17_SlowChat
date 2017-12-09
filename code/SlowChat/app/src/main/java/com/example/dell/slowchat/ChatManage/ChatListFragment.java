package com.example.dell.slowchat.ChatManage;

/**
 * Created by dell on 2017/11/28.
 */


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.slowchat.R;

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
    private SQLiteOp sqLiteOp;


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
        this.sqLiteOp=new SQLiteOp(getContext());
        this.iniChatList(rootView);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    private void initShowVieW(View rootView )
    {
        TextView textView = (TextView) rootView.findViewById(R.id.chat_manage_show);
        int position=getArguments().getInt(ARG_SECTION_NUMBER);
        String showText="ChatList index:"+String.valueOf(position);
        textView.setText(showText);
    }


    private void iniChatList(View rootView ){
        initChatInfos();
        this.chatList=(ListView)rootView.findViewById(R.id.chat_manage_chat_list);
        MyBaseAdapter myBaseAdapter=new MyBaseAdapter(getContext(),this.sqLiteOp,this.chatInfos);
        this.chatList.setAdapter(myBaseAdapter);
    }


    private void initChatInfos(){
        this.chatInfos=new ArrayList<>();
        ChatInfo chatInfo=new ChatInfo(1,"张三","12月4号","你好",1,getPortrait(1));
        this.chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(2,"李四","12月4号","还好吗",2,getPortrait(2));
        this.chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(3,"王五","12月1号","昨天多谢了",0,getPortrait(3));
        this.chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(4,"这就是传说中的。。","11月27号","有啥事啊",0,getPortrait(4));
        this.chatInfos.add(chatInfo);
        chatInfo=new ChatInfo(5,"perfect","11月30号","what",0,getPortrait(5));
        this.chatInfos.add(chatInfo);
    }

    private Drawable getPortrait(int position){
        String pictureName="portrait"+String.valueOf(position);
        int picID=getPictureID(pictureName);
        if (picID!=0) {
            return getResources().getDrawable(picID);
        }
        return null;
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

}
