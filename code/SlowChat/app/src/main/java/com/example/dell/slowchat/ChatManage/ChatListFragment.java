package com.example.dell.slowchat.ChatManage;

/**
 * Created by dell on 2017/11/28.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.slowchat.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatListFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView icon;
    private TextView text;

    public ChatListFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChatListFragment newInstance(int sectionNumber)
    {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.chat_manage_main, container, false);
        this.initShowVieW(rootView);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    private void initShowVieW(View rootView )
    {
        TextView textView = (TextView) rootView.findViewById(R.id.show_chat_manage);
        int position=getArguments().getInt(ARG_SECTION_NUMBER);
        String showText="ChatList index:"+String.valueOf(position);
        textView.setText(showText);
    }



    private void initTab()
    {
        this.resetTab();
        this.selectTab();
    }

    private void selectTab()
    {
        this.icon=(ImageView)getActivity().findViewById(R.id.chat_manage_image_main);
        this.icon.setSelected(true);
        this.text=(TextView)getActivity().findViewById(R.id.chat_manage_text_main);
        this.text.setSelected(true);
        this.text.setTextColor(getResources().getColor(R.color.mainSelectedText));
    }

    private void resetTab()
    {
        ImageView friendManageI=(ImageView)getActivity().findViewById(R.id.friend_manage_image_main);
        TextView friendManageT=(TextView)getActivity().findViewById(R.id.friend_manage_text_main);
        friendManageT.setTextColor(getResources().getColor(R.color.mainNormalText));

        ImageView personalInfoI=(ImageView)getActivity().findViewById(R.id.personal_info_image_main);
        TextView personalInfoT=(TextView)getActivity().findViewById(R.id.personal_info_text_main);
        personalInfoT.setTextColor(getResources().getColor(R.color.mainNormalText));

    }

}
