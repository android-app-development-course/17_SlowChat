package com.example.dell.slowchat.MakeFriendHall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.dell.slowchat.R;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_hall_add_friend);
        Toolbar toolbar=(Toolbar)findViewById(R.id.friend_add_toolbar);
        setSupportActionBar(toolbar);
    }
}
