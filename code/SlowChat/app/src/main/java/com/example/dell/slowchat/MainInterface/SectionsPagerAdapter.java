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
public class SectionsPagerAdapter extends FragmentPagerAdapter
{


    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ChatListFragment (defined as a static inner class below).
        switch (position){
            case 0:

                return ChatListFragment.newInstance(1);
            case 1:

                return FriendManageFragment.newInstance(2);
            case 2:
                return PersonalInfoFragment.newInstance(3);
            default:
                return null;
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
