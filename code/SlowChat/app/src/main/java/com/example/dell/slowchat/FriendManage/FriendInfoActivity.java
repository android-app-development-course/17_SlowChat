package com.example.dell.slowchat.FriendManage;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.slowchat.ChatManage.ChatInfo;
import com.example.dell.slowchat.ChatManage.ChatInterface;
import com.example.dell.slowchat.ChatManage.CircleImageView;

import com.example.dell.slowchat.ChatManage.SQLiteOp;
import com.example.dell.slowchat.MainInterface.MainActivity;
import com.example.dell.slowchat.R;

public class FriendInfoActivity extends AppCompatActivity {
    private CircleImageView img;
    private TextView nameText2;
    private TextView sigText2;
    private TextView uidText2;
    private TextView sexText2;
    private TextView ageText2;
    private TextView birthText2;
    private TextView addrText2;
    private TextView relationText2;
    private TextView tagText2;
    private Button chatBtn;
    private Button deleteBtn;

    private SQLiteDatabase writeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_manage_friend_info);
        img=(CircleImageView)findViewById(R.id.friend_info_headIcon2);
        nameText2=(TextView)findViewById(R.id.friend_info_nameText2);
        sigText2=(TextView)findViewById(R.id.friend_info_sigText2);
        uidText2=(TextView)findViewById(R.id.friend_info_uid);
        sexText2=(TextView)findViewById(R.id.friend_info_sex);
        ageText2=(TextView)findViewById(R.id.friend_info_age);
        birthText2=(TextView)findViewById(R.id.friend_info_birthday);
        addrText2=(TextView)findViewById(R.id.friend_info_addr);
        relationText2=(TextView)findViewById(R.id.friend_info_relationText2);
        tagText2=(TextView)findViewById(R.id.friend_info_tag);
        chatBtn=(Button)findViewById(R.id.friend_info_message);
        deleteBtn=(Button)findViewById(R.id.friend_info_delete);

        writeDB=new SQLiteOp(this).getWritableDatabase();
        initWidget();
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入聊天页面
                Intent intent=new Intent(FriendInfoActivity.this,ChatInterface.class);
                int friendID=getFriendID();
                intent.putExtra("friend_id",friendID);
                String friendName=getFriendName();
                intent.putExtra("name",friendName);
                startActivityForResult(intent,1);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除好友
                deleteFriend("你确认要删除好友吗？");
            }
        });

    }

    private void deleteFriend(String tip){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(tip)
                .setPositiveButton("确认" ,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                deleteDataInDB(getFriendID());
                                returnToMainActivity();
                            }
                        }
                )
                .setNegativeButton("取消" ,null)
                .show();
    }



    private void deleteDataInDB(int friendId){
        String whereClause = "friend_id=?";
        String[] whereArgs = {String.valueOf(friendId)};
        writeDB.delete("friend",whereClause,whereArgs);
        writeDB.delete("message",whereClause,whereArgs);
    }

    private void returnToMainActivity(){
        Intent intent=new Intent();
        intent.putExtra("friend_id",getFriendID());
        setResult(1,intent);
        finish();
    }


    private void initWidget(){
        nameText2.setText(getFriendName());
        sigText2.setText("萌新请多多关照！");
        uidText2.setText("    账号    "+getFriendID());
        sexText2.setText("    性别    女");
        ageText2.setText("    年龄    18");
        birthText2.setText("    生日    7 月 25 日");
        addrText2.setText("    故乡    浙江-杭州");
        relationText2.setText("  亲密度    0");
        tagText2.setText("    标签    萌系 动漫 运动 游戏");
        img.setImageDrawable(ChatInfo.friendPortrait);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writeDB.close();
    }
}
