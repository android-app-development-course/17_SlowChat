package com.example.dell.slowchat.MakeFriendHall;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dell.slowchat.R;

public class MakeFriendHall extends AppCompatActivity {
   // private ImageView friendHallItemImg;
    //private FriendHallGvAdapter gvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_hall_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.freind_hall_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_sc_content);
        layout.removeAllViews();
        for (int i = 0; i <15; i++) {
            View view = View.inflate(MakeFriendHall.this, R.layout.friend_hall_item, null);
        //    ((TextView) view.findViewById(R.id.friend_hall_text)).setText("item"+String.valueOf(i));
            //动态添加 子View
            GridView friendHallGv=(GridView) view.findViewById(R.id.friend_hall_gv);
            FriendHallGvAdapter gvAdapter=new FriendHallGvAdapter();
            friendHallGv.setAdapter(gvAdapter);
            layout.addView(view, i);
        }
    }

    public class FriendHallGvAdapter extends BaseAdapter {
        public int getCount(){
            return 3;
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
            friendHallItemImg.setImageResource(R.drawable.portrait_you);
            return view;

        }
        }

}
