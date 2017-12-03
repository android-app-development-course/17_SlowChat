package com.example.dell.slowchat.PersonalInformation;

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
public class PersonalInfoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView icon;
    private TextView text;

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
        this.initShowText(rootView);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    private void initShowText(View rootView)
    {
        TextView textView = (TextView) rootView.findViewById(R.id.show_person_info);
        int position = getArguments().getInt(ARG_SECTION_NUMBER);
        String showText = "PersonInf index:" + String.valueOf(position);
        textView.setText(showText);
    }



}
