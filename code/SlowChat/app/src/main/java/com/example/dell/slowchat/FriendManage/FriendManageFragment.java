package com.example.dell.slowchat.FriendManage;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.example.dell.slowchat.R;

import java.lang.reflect.Field;
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
    private ImageView icon;
    private TextView text;


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
        vf = new Vector<>();
        this.initShowView(rootView);
        friendShowAdapter = new FriendShowAdapter();
        friendShowLv = (ListView) rootView.findViewById(R.id.friend_show_lv);
        friendShowLv.setAdapter(friendShowAdapter);
        setHasOptionsMenu(true);
        friendShowLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击好友进入好友信息界面
                Intent intent = new Intent(((AppCompatActivity) getActivity()).getBaseContext(), FriendInfoActivity.class);
                intent.putExtra("Object", vf.get(i));
                //      intent.putExtra("BITMAP",vf.get(i).getHeadIcon());
                startActivity(intent);
            }
        });
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }


    private void initShowView(View rootView) {
        /*
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        int position=getArguments().getInt(ARG_SECTION_NUMBER);
        String showText="FriendManage index:"+String.valueOf(position);
        textView.setText(showText);
        */
    }
/*
    private void initTab()
    {
        this.resetTab();
        this.selectTab();
    }

    private void selectTab()
    {
        this.icon=(ImageView)getActivity().findViewById(R.id.friend_manage_image_main);
        this.icon.setSelected(true);
        this.text=(TextView)getActivity().findViewById(R.id.friend_manage_text_main);
        this.text.setSelected(true);
        this.text.setTextColor(getResources().getColor(R.color.mainSelectedText));
    }

    private void resetTab()
    {
        ImageView chatManageI=(ImageView)getActivity().findViewById(R.id.chat_manage_image_main);
        TextView chatManageT=(TextView)getActivity().findViewById(R.id.chat_manage_text_main);
        chatManageT.setTextColor(getResources().getColor(R.color.mainNormalText));

        ImageView personalInfoI=(ImageView)getActivity().findViewById(R.id.personal_info_image_main);
        TextView personalInfoT=(TextView)getActivity().findViewById(R.id.personal_info_text_main);
        personalInfoT.setTextColor(getResources().getColor(R.color.mainNormalText));
    }
    */


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
            return 5;
        }

        public Friend getItem(int position) {
            String[]userName={"张三","李四","王五","小明","小红"};
            Friend f = new Friend();
            f.setUid(String.valueOf(position + 1));
            f.setUserName(userName[position]);
            f.setRelationLevel(0);
//            f.setHeadIcon(BitmapFactory.decodeResource(getResources(), R.drawable.portrait_you));
            f.setHeadIcon(((BitmapDrawable) getPortrait(position+1)).getBitmap());
            f.setAge(18);
            f.setSex("女");
            f.setBirthday("7 月 25 日");
            f.setTag("萌系 动漫 运动 游戏");
            f.setAddr("浙江-杭州");
            vf.add(f);
            return f;
        }


        private Drawable getPortrait(int position) {
            String pictureName = "portrait" + String.valueOf(position);
            int picID = getPictureID(pictureName);
            if (picID != 0) {
                return ContextCompat.getDrawable(getContext(), picID);
            }
            return null;
        }

        public int getPictureID(String pictureName) {
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

        public View getView(int position, View ConvertVinullew, ViewGroup parent) {
            View view = View.inflate(getActivity().getBaseContext(), R.layout.friend_manage_main_show_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.friend_show_headIcon);
            TextView nameText = (TextView) view.findViewById(R.id.friend_show_nameText),
                    relationText = (TextView) view.findViewById(R.id.friend_show_relationText);
            Friend f = getItem(position);
            img.setImageBitmap(f.getHeadIcon());
            nameText.setText(f.getUserName());
            relationText.setText("亲密度:" + String.valueOf(f.getRelationLevel()));
            return view;
        }

        public long getItemId(int position) {
            return position;
        }
    }

}