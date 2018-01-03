package com.example.dell.slowchat.PersonalInformation;

/**
 * Created by dell on 2017/11/28.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.slowchat.Login.UserInfo;
import com.example.dell.slowchat.Login.UserInfoSQLiteHelper;
import com.example.dell.slowchat.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PersonalInfoFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView userImage;
    private TextView userName;
    private TextView userAccount;
    private TextView userIntegral;

    private UserInfoSQLiteHelper userInfoSQLiteHelper;

    public PersonalInfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PersonalInfoFragment newInstance(int sectionNumber) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.personal_info_main, container, false);
//        this.initShowText(rootView);

        this.initView(rootView);
        this.initObject();
        this.initListener();
        this.initPersonalInfo();
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        return rootView;
    }

    private void initShowText(View rootView)
    {
//        TextView textView = (TextView) rootView.findViewById(R.id.show_person_info);
        int position = getArguments().getInt(ARG_SECTION_NUMBER);
        String showText = "PersonInf index:" + String.valueOf(position);
//        textView.setText(showText);
    }

    private String getUserEmail()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);
        return userEmail;
    }

    private void initView(View rootView)
    {
        userName = (TextView) rootView.findViewById(R.id.personal_info_user_name);
        userAccount = (TextView) rootView.findViewById(R.id.personal_info_user_account);
        userIntegral = (TextView) rootView.findViewById(R.id.personal_info_user_integral);
        userImage = (ImageView) rootView.findViewById(R.id.personal_info_user_image);
    }

    private void initListener()
    {
        userImage.setOnClickListener(this);
    }

    private void initObject()
    {
        userInfoSQLiteHelper = new UserInfoSQLiteHelper(this.getContext());

    }

    private void initPersonalInfo()
    {
        UserInfo userInfo = userInfoSQLiteHelper.getUserInfo(this.getUserEmail());
        userName.setText(userInfo.getUserName());
        userAccount.setText(userInfo.getUserEmail());
//        userIntegral.setText(userInfo.getUserIntegral());
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.personal_info_user_image:
                Toast.makeText(this.getContext(), "点击了头像", Toast.LENGTH_SHORT).show();
                break;
        }
    }




    }
