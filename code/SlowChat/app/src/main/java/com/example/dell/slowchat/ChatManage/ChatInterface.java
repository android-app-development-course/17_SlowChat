package com.example.dell.slowchat.ChatManage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dell.slowchat.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatInterface extends AppCompatActivity {

    private Button BtnSend;
    private EditText InputBox;
    private ArrayList<ChatMsg> mData;
    private ChatBaseAdapter mAdapter;
    private ListView mListView;

    private Drawable[] portraits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_manage_chat);
        initPortrait();
        initListView();
        InputBox=(EditText)findViewById(R.id.chat_manage_chat_input);
        initSendBtn();
    }




    private void initListView(){
        mListView=(ListView)findViewById(R.id.chat_manage_chat_msg_list);
        mData=LoadData();
        mAdapter=new ChatBaseAdapter(this,portraits,mData);
        mListView.setAdapter(mAdapter);
        mListView.smoothScrollToPositionFromTop(mData.size(), 0);
    }




    public void initPortrait(){
        portraits=new Drawable[2];
        portraits[ChatMsg.MessageTypeGet]= ContextCompat.getDrawable(this, R.drawable.portrait_you);
        portraits[ChatMsg.MessageTypeSend]= ContextCompat.getDrawable(this,R.drawable.portrait_me);
    }


    private void initSendBtn(){
        BtnSend=(Button)findViewById(R.id.chat_manage_chat_send);
        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                String inputString=InputBox.getText().toString();
                if(!inputString.equals(""))
                {
                    //获取时间
                    Calendar c=Calendar.getInstance();
                    StringBuilder mBuilder=new StringBuilder();
                    mBuilder.append(Integer.toString(c.get(Calendar.YEAR))+"年");
                    mBuilder.append(Integer.toString(c.get(Calendar.MONTH))+"月");
                    mBuilder.append(Integer.toString(c.get(Calendar.DATE))+"日");
                    mBuilder.append(Integer.toString(c.get(Calendar.HOUR_OF_DAY))+":");
                    mBuilder.append(Integer.toString(c.get(Calendar.MINUTE)));
                    //构造时间消息
                    ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeTime,mBuilder.toString());
                    mData.add(Message);
                    //构造输入消息
                    Message=new ChatMsg(ChatMsg.MessageTypeSend,inputString);
                    mData.add(Message);
                    //构造返回消息，如果这里加入网络的功能，那么这里将变成一个网络机器人
                    Message=new ChatMsg(ChatMsg.MessageTypeGet,"收到！");
                    mData.add(Message);
                    //更新数据
                    mAdapter.Refresh();
                }
                //清空输入框
                InputBox.setText("");
                //关闭输入法
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
                //滚动列表到当前消息
                mListView.smoothScrollToPositionFromTop(mData.size(), 0);
            }
        });
    }

    private ArrayList<ChatMsg> LoadData()
    {
        ArrayList<ChatMsg> Messages=new ArrayList<>();

        ChatMsg Message=new ChatMsg(ChatMsg.MessageTypeTime,"2013年12月27日");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"山重水复疑无路");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"柳暗花明又一村");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"青青子衿，悠悠我心");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"但为君故，沉吟至今");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeTime,"19：25");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"这是你做的Android程序吗？");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"是的，这是一个仿微信的聊天界面");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"为什么下面的消息发送不了呢");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"呵呵，我会告诉你那是直接拿图片做的么");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"哦哦，呵呵，你又在偷懒了");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"因为这一部分不是今天的重点啊");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeGet,"好吧，可是怎么发图片啊");
        Messages.add(Message);

        Message=new ChatMsg(ChatMsg.MessageTypeSend,"很简单啊，你继续定义一种布局类型，然后再写一个布局就可以了");
        Messages.add(Message);
        return Messages;
    }

}
