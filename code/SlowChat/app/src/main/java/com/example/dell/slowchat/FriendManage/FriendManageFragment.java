package com.example.dell.slowchat.FriendManage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.slowchat.R;

/**
 * Created by dell on 2017/12/3.
 */

public class FriendManageFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView icon;
    private TextView text;

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
        this.initShowView(rootView);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }


    private void initShowView(View rootView)
    {
        TextView textView = (TextView) rootView.findViewById(R.id.show_friend_manage);
        int position=getArguments().getInt(ARG_SECTION_NUMBER);
        String showText="FriendManage index:"+String.valueOf(position);
        textView.setText(showText);
    }

}
