package com.example.dell.slowchat.MainInterface;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.slowchat.ChatManage.ChatListFragment;
import com.example.dell.slowchat.FriendManage.FriendManageFragment;
import com.example.dell.slowchat.PersonalInformation.PersonalInfoFragment;
import com.example.dell.slowchat.R;

public class MainActivity extends AppCompatActivity
{
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private ImageView chatManageI;
    private TextView chatManageT;

    private ImageView friendManageI;
    private TextView friendManageT;

    private ImageView personalInfoI;
    private TextView personalInfoT;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        initViewPage();
        initLayout();
        initTab();
    }


    private void initViewPage()
    {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        resetTab(1);
                        resetTab(2);
                        selectTab(0);
                        break;
                    case 1:
                        resetTab(0);
                        resetTab(2);
                        selectTab(1);
                        break;
                    case 2:
                        resetTab(0);
                        resetTab(1);
                        selectTab(2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initLayout()
    {
        LinearLayout chatManageLL=(LinearLayout)findViewById(R.id.main_chat_manage_ll);
        chatManageLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        LinearLayout friendManageLL=(LinearLayout)findViewById(R.id.main_friend_manage_ll);
        friendManageLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        LinearLayout personalInfoLL=(LinearLayout)findViewById(R.id.main_personal_info_ll);
        personalInfoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });
    }



    private void initTab()
    {
        this.chatManageI=(ImageView)findViewById(R.id.main_chat_manage_image);
        this.chatManageT=(TextView)findViewById(R.id.main_chat_manage_text);
        this.friendManageI=(ImageView)findViewById(R.id.main_friend_manage_image);
        this.friendManageT=(TextView)findViewById(R.id.main_friend_manage_text);
        this.personalInfoI=(ImageView)findViewById(R.id.main_personal_info_image);
        this.personalInfoT=(TextView)findViewById(R.id.main_personal_info_text);

        this.initTabControl();
    }


    private void initTabControl()
    {
        selectTab(0);
    }

    private void resetTab(int position)
    {
        switch (position)
        {
            case 0:
                chatManageT.setTextColor(ContextCompat.getColor(this,R.color.mainNormalText));
                chatManageT.setTextSize(getResources().getDimension(R.dimen.main_normal_font));
                break;
            case 1:
                friendManageT.setTextColor(ContextCompat.getColor(this,R.color.mainNormalText));
                friendManageT.setTextSize(getResources().getDimension(R.dimen.main_normal_font));
                break;
            case 2:
                personalInfoT.setTextColor(ContextCompat.getColor(this,R.color.mainNormalText));
                personalInfoT.setTextSize(getResources().getDimension(R.dimen.main_normal_font));
        }
    }

    private void selectTab(int position)
    {
        switch (position)
        {
            case 0:
                chatManageT.setTextColor(ContextCompat.getColor(this,R.color.mainSelectedText));
                chatManageT.setTextSize(getResources().getDimension(R.dimen.main_selected_font));
                break;
            case 1:
                friendManageT.setTextColor(ContextCompat.getColor(this,R.color.mainSelectedText));
                friendManageT.setTextSize(getResources().getDimension(R.dimen.main_selected_font));
                break;
            case 2:
                personalInfoT.setTextColor(ContextCompat.getColor(this,R.color.mainSelectedText));
                personalInfoT.setTextSize(getResources().getDimension(R.dimen.main_selected_font));
        }
    }


}
