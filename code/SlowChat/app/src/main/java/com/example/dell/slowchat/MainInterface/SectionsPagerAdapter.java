package com.example.dell.slowchat.MainInterface;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.slowchat.ChatManage.ChatListFragment;
import com.example.dell.slowchat.FriendManage.FriendManageFragment;
import com.example.dell.slowchat.PersonalInformation.PersonalInfoFragment;
import com.example.dell.slowchat.R;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private ImageView chatManageI;
    private TextView chatManageT;

    private ImageView friendManageI;
    private TextView friendManageT;

    private ImageView personalInfoI;
    private TextView personalInfoT;

    private View mainView;

    public SectionsPagerAdapter(FragmentManager fm,Context context)
    {
        super(fm);
        this.mainView= LayoutInflater.from(context).inflate(R.layout.activity_main,null);
        initTab();
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ChatListFragment (defined as a static inner class below).
        switch (position){
            case 0:
                this.selectTab(0);
                this.resetTab(1);
                this.resetTab(2);
                return ChatListFragment.newInstance(1);
            case 1:
                this.selectTab(1);
                this.resetTab(0);
                this.resetTab(2);
                return FriendManageFragment.newInstance(2);
            case 2:
                this.selectTab(2);
                this.resetTab(0);
                this.resetTab(1);
                return PersonalInfoFragment.newInstance(3);
            default:
                return null;
        }
    }


    private void initTab()
    {
        this.chatManageI=(ImageView)this.mainView.findViewById(R.id.chat_manage_image_main);
        this.chatManageT=(TextView)this.mainView.findViewById(R.id.chat_manage_text_main);
        this.friendManageI=(ImageView)this.mainView.findViewById(R.id.friend_manage_image_main);
        this.friendManageT=(TextView)this.mainView.findViewById(R.id.friend_manage_text_main);
        this.personalInfoI=(ImageView)this.mainView.findViewById(R.id.personal_info_image_main);
        this.personalInfoT=(TextView)this.mainView.findViewById(R.id.personal_info_text_main);
    }

    private void resetTab(int position)
    {
        switch (position)
        {
            case 0:
                chatManageT.setTextColor(this.mainView.getResources().getColor(R.color.mainNormalText));
                break;
            case 1:
                friendManageT.setTextColor(this.mainView.getResources().getColor(R.color.mainNormalText));
                break;
            case 2:
                personalInfoT.setTextColor(this.mainView.getResources().getColor(R.color.mainNormalText));
        }
    }

    private void selectTab(int position)
    {
        switch (position)
        {
            case 0:
                chatManageT.setTextColor(this.mainView.getResources().getColor(R.color.mainSelectedText));
                break;
            case 1:
                friendManageT.setTextColor(this.mainView.getResources().getColor(R.color.mainSelectedText));
                break;
            case 2:
                personalInfoT.setTextColor(this.mainView.getResources().getColor(R.color.mainSelectedText));
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}
