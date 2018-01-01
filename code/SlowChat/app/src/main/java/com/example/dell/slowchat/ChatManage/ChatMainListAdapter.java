package com.example.dell.slowchat.ChatManage;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.slowchat.R;
import com.example.dell.slowchat.badgeview.BGABadgeTextView;

import java.util.ArrayList;

/**
 * Created by dell on 2017/12/1.
 */

public class ChatMainListAdapter extends BaseAdapter {
    private ArrayList<ChatInfo> chatInfos;
    private LayoutInflater mInflater;
    private Context mainContext;


    public ChatMainListAdapter(Context context, ArrayList<ChatInfo> chatInfos){
        this.chatInfos=chatInfos;
        this.mInflater = LayoutInflater.from(context);
        this.mainContext=context;
    }

    public void refresh()
    {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.chatInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.chat_manage_main_list_item, parent,false);
            holder.portrait = (ImageView) convertView.findViewById(R.id.chat_mange_main_list_portrait);
            holder.name = (TextView)convertView.findViewById(R.id.chat_mange_main_list_name);
            holder.time=(TextView) convertView.findViewById(R.id.chat_mange_main_list_time);
            holder.content=(TextView)convertView.findViewById(R.id.chat_mange_main_list_content);
            holder.messageNum=(BGABadgeTextView)convertView.findViewById(R.id.chat_mange_main_list_message_num);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.init(chatInfos.get(position));
        return convertView;
    }

//    // listview中点击按键弹出对话框
//    private void showInfo(final int position) {
//        new AlertDialog.Builder(this.mainContext).setTitle("我的提示").setMessage("确定要删除吗？")
//                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        deleteDataInDB(position);
//                        // 通过程序我们知道删除了，但是怎么刷新ListView呢？
//                        // 只需要重新设置一下adapter
//                        notifyDataSetChanged();
//                    }}
//                )
//                .setNegativeButton("取消",null)
//                .show();
//    }
//
//    private void deleteDataInDB(int position){
//        SQLiteDatabase db=this.sqLiteOp.getWritableDatabase();
//        String whereClause = "id=?";
//        String[] whereArgs = {String.valueOf(this.chatInfos.get(position).getFriendId())};
//        db.delete("message",whereClause,whereArgs);
//       db.close();
//    }


    private class ViewHolder {

        private ImageView portrait;

        private TextView name;

        private TextView time;

        private TextView content;

        private BGABadgeTextView messageNum;

        private void init(ChatInfo chatInfo) {
            this.portrait.setImageDrawable(chatInfo.getPortrait());
            this.name.setText(chatInfo.getName());
            this.time.setText(chatInfo.getTime());
            this.content.setText(chatInfo.getContent());
            if(chatInfo.getMsgNum()!=0){
                messageNum.showCirclePointBadge();
                messageNum.showTextBadge(String.valueOf(chatInfo.getMsgNum()));
            }else {
                messageNum.hiddenBadge();
            }
        }
    }
}
