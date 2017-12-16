package com.example.dell.slowchat.ChatManage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.slowchat.R;

import java.util.ArrayList;

/**
 * Created by dell on 2017/12/1.
 */

public class ChatBaseAdapter extends BaseAdapter {
    private ArrayList<ChatMsg> chatMsgs;
    private Drawable[] portraits;
    private LayoutInflater mInflater;
    private Context mainContext;


    public ChatBaseAdapter(Context context,Drawable[] portraits,ArrayList<ChatMsg> chatMsgs){
        this.portraits=portraits;
        this.chatMsgs=chatMsgs;
        this.mInflater = LayoutInflater.from(context);
        this.mainContext=context;
    }



    @Override
    public int getCount() {
        return this.chatMsgs.size();
    }

    public void Refresh()
    {
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return chatMsgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView Content;
        CircleImageView portrait;
        switch(chatMsgs.get(position).getType())
        {
            case ChatMsg.MessageTypeTime:
                convertView=mInflater.inflate(R.layout.chat_manage_chat_send_time_item, parent,false);
                Content=(TextView)convertView.findViewById(R.id.chat_mange_chat_send_time_text);
                Content.setText(chatMsgs.get(position).getContent());
                break;
            case ChatMsg.MessageTypeGet:
                convertView=mInflater.inflate(R.layout.chat_manage_chat_get_msg_item, parent,false);
                Content=(TextView)convertView.findViewById(R.id.chat_manage_chat_get_msg);
                Content.setText(chatMsgs.get(position).getContent());
                portrait=(CircleImageView) convertView.findViewById(R.id.chat_manage_chat_portrait);
                portrait.setImageDrawable(portraits[ChatMsg.MessageTypeGet]);
                break;
            case ChatMsg.MessageTypeSend:
                convertView=mInflater.inflate(R.layout.chat_manage_chat_send_msg_item, parent,false);
                Content=(TextView)convertView.findViewById(R.id.chat_manage_chat_send_msg);
                Content.setText(chatMsgs.get(position).getContent());
                portrait=(CircleImageView) convertView.findViewById(R.id.chat_manage_chat_portrait);
                portrait.setImageDrawable(portraits[ChatMsg.MessageTypeSend]);
                break;
        }
        return convertView;
    }



//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            holder=new ViewHolder();
//            switch(chatMsgs.get(position).getType())
//            {
//                case ChatMsg.MessageTypeTime:
//                    convertView = mInflater.inflate(R.layout.chat_manage_chat_send_time_item, parent,false);
//                    holder.content=(TextView)convertView.findViewById(R.id.chat_mange_chat_send_time_text);
//                    break;
//                case ChatMsg.MessageTypeGet:
//                    convertView = mInflater.inflate(R.layout.chat_manage_chat_get_msg_item, parent,false);
//                    holder.portrait=(CircleImageView) convertView.findViewById(R.id.chat_manage_chat_portrait);
//                    holder.content=(TextView)convertView.findViewById(R.id.chat_manage_chat_get_msg);
//                    break;
//                case ChatMsg.MessageTypeSend:
//                    convertView = mInflater.inflate(R.layout.chat_manage_chat_send_msg_item, parent,false);
//                    holder.portrait=(CircleImageView) convertView.findViewById(R.id.chat_manage_chat_portrait);
//                    holder.content=(TextView)convertView.findViewById(R.id.chat_manage_chat_send_msg);
//                    break;
//            }
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder)convertView.getTag();
//        }
//        holder.init(chatMsgs.get(position));
//
//        return convertView;
//    }



    // listview中点击按键弹出对话框
    private void showInfo(final int position) {
        new AlertDialog.Builder(this.mainContext).setTitle("我的提示").setMessage("确定要删除吗？")
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        notifyDataSetChanged();
                    }}
                )
                .setNegativeButton("取消",null)
                .show();
    }



    private class ViewHolder {

        private CircleImageView portrait;

        private TextView content;

        private void init(ChatMsg chatMsg) {
            if(chatMsg.getType()==ChatMsg.MessageTypeGet){
                portrait.setImageDrawable(portraits[ChatMsg.MessageTypeGet]);
            }
            if(chatMsg.getType()==ChatMsg.MessageTypeSend){
                portrait.setImageDrawable(portraits[ChatMsg.MessageTypeSend]);
            }
            this.content.setText(chatMsg.getContent());
        }
    }
}
