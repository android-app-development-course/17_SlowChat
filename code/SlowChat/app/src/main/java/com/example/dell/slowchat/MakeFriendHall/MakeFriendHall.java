package com.example.dell.slowchat.MakeFriendHall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.dell.slowchat.ChatManage.CircleImageView;
import com.example.dell.slowchat.R;

public class MakeFriendHall extends AppCompatActivity {
   // private ImageView friendHallItemImg;
    //private FriendHallGvAdapter gvAdapter;
    private ListView friendHallLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_hall_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.friend_hall_toolbar);
        setSupportActionBar(toolbar);
        FriendHallLvAdapter friendHallLvAdapter=new FriendHallLvAdapter();
        friendHallLv=(ListView)findViewById(R.id.friend_hall_lv);
        Log.i("ListView","getSucceed");
        friendHallLv.setAdapter(friendHallLvAdapter);
        friendHallLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });





    }

    public class FriendHallGvAdapter extends BaseAdapter {
        public int getCount(){
            return 1;
        }
        public Bitmap getItem(int pos){
            return null;
        }
        public long getItemId(int pos){
            return pos;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= View.inflate(MakeFriendHall.this,R.layout.friend_hall_gvitem,null);
            ImageView friendHallItemImg=(ImageView) view.findViewById(R.id.friend_hall_img);
            friendHallItemImg.setImageResource(R.drawable.friend_hall_example);
            return view;

        }
    }

    public class FriendHallLvAdapter extends BaseAdapter {
        public int getCount(){
            return 5;
        }
        public Object getItem(int pos){return null;}
        public long getItemId(int pos){return pos;}
        public View getView(int pos, View convertView, ViewGroup Parent){
            int position=pos;
            View view= View.inflate(MakeFriendHall.this,R.layout.friend_hall_item,null);
            CircleImageView img=(CircleImageView)view.findViewById(R.id.friend_hall_headIcon);
            img.setImageResource(R.drawable.portrait_other);
            TextView nameText=(TextView)view.findViewById(R.id.friend_hall_name),
                    contentText=(TextView)view.findViewById(R.id.friend_hall_dynamicString);
            nameText.setText("Neptune");
            contentText.setText("今天17号线开通啦");

            FriendHallGvAdapter friendHallGvAdapter=new FriendHallGvAdapter();
            GridView gridView=(GridView)view.findViewById(R.id.friend_hall_gv);
            gridView.setAdapter(friendHallGvAdapter);
            Log.i("ListView","DataSucceed");

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MakeFriendHall.this,AddFriendActivity.class);
                    startActivity(intent);
//                    Toast.makeText(MakeFriendHall.this,"您点击了第"+ String.valueOf(1)+"个ImageView", Toast.LENGTH_LONG);

                }
            });


            return view;
        }
    }

}
