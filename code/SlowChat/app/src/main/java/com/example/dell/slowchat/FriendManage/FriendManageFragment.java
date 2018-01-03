package com.example.dell.slowchat.FriendManage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.slowchat.ChatManage.ChatInfo;
import com.example.dell.slowchat.ChatManage.SQLiteOp;
import com.example.dell.slowchat.R;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link FriendManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendManageFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SQLiteDatabase readDB;


    private ListView friendShowLv;
    private FriendShowAdapter friendShowAdapter;

    private Vector<Friend> vf;

    public FriendManageFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FriendManageFragment newInstance(int sectionNumber) {
        FriendManageFragment fragment = new FriendManageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friend_manage_main, container, false);
        readDB=new SQLiteOp(getContext()).getReadableDatabase();
        initListViewData();
        initShowView(rootView);
        return rootView;
    }

    private void setFriendBitmap(int position){
        Drawable portrait=new BitmapDrawable(getResources(),vf.get(position).getHeadIcon());
        ChatInfo.friendPortrait=portrait;
    }


    private void initShowView(View rootView) {
        friendShowAdapter = new FriendShowAdapter();
        friendShowLv = (ListView) rootView.findViewById(R.id.friend_show_lv);
        friendShowLv.setAdapter(friendShowAdapter);
        setHasOptionsMenu(true);
        friendShowLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击好友进入好友信息界面
                Intent intent = new Intent((getActivity()).getBaseContext(), FriendInfoActivity.class);
                int friendID=vf.get(i).getUid();
                intent.putExtra("friend_id",friendID);
                String friendName=vf.get(i).getUserName();
                intent.putExtra("name",friendName);
                setFriendBitmap(i);
                startActivityForResult(intent,2);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==1){
            int friendId=data.getIntExtra("friend_id",0);
            resetListData(friendId);
        }
    }


    private void resetListData(int friendId){
        if(friendId==0)
            return;
        for (Friend friend:vf){
            if(friend.getUid()==friendId){
                vf.remove(friend);
                break;
            }
        }
        friendShowAdapter.refresh();
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.friend_show_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("输入好友账号或用户名");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public class FriendShowAdapter extends BaseAdapter {
        public int getCount() {
            return vf.size();
        }

        public Friend getItem(int position) {
            return vf.get(position);
        }


        public void refresh(){
            notifyDataSetChanged();
        }

        public View getView(int position, View ConvertVinullew, ViewGroup parent) {
            View view = View.inflate(getActivity().getBaseContext(), R.layout.friend_manage_main_show_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.friend_show_headIcon);
            TextView nameText = (TextView) view.findViewById(R.id.friend_show_nameText),
                    relationText = (TextView) view.findViewById(R.id.friend_show_relationText);
            Friend f = getItem(position);
            img.setImageBitmap(f.getHeadIcon());
            nameText.setText(f.getUserName());
            relationText.setText("亲密度:0");
            return view;
        }

        public long getItemId(int position) {
            return position;
        }
    }

    private void initListViewData(){
        vf = new Vector<>();
        getFriendInfoFromDB();
    }


    private void getFriendInfoFromDB() {
        String[] columns = {"friend_id","name", "portrait"};
        Cursor cursor = readDB.query("friend", columns, null, null, null, null, null);
        //判断游标是否为空
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] portrait = cursor.getBlob(2);
                Bitmap dBitmap=exchangeByteToBitmap(portrait);
                Friend f = new Friend();
                f.setUid(id);
                f.setUserName(name);
                f.setHeadIcon(dBitmap);
                vf.add(f);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private Bitmap exchangeByteToBitmap(byte[] blob){
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        return bmp;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        readDB.close();
    }
}