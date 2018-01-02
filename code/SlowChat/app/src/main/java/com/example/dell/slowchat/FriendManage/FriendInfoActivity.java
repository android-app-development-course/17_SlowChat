package com.example.dell.slowchat.FriendManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        Intent intent=getIntent();
        Friend f=(Friend)intent.getParcelableExtra("Object");
       // Bitmap mp=intent.getParcelableExtra("BITMAP");
        nameText2.setText(f.getUserName());
        sigText2.setText(f.getSignature());
        uidText2.setText("    账号    "+f.getUid());
        sexText2.setText("    性别    "+f.getSex());
        ageText2.setText("    年龄    "+f.getAge());
        birthText2.setText("    生日    "+f.getBirthday());
        addrText2.setText("    故乡    "+f.getAddr());
        relationText2.setText("  亲密度    "+f.getRelationLevel());
        tagText2.setText("    标签    "+f.getTag());
   //  img.setImageBitmap(mp);
        img.setImageResource(R.drawable.portrait1);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入聊天页面
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除好友
            }
        });

    }
}
