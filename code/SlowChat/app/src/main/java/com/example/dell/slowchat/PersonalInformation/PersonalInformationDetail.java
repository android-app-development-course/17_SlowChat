package com.example.dell.slowchat.PersonalInformation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.slowchat.Login.UserInfo;
import com.example.dell.slowchat.Login.UserInfoSQLiteHelper;
import com.example.dell.slowchat.R;

public class PersonalInformationDetail extends AppCompatActivity implements View.OnClickListener{

    private String userEmail;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView userIntegralTextView;
    private TextView userSignatureTextView;
    private UserInfo userInfo;
    private UserInfoSQLiteHelper userInfoSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_detail);

        InitObject();
        InitView();
        InitListener();
        InitTextView();
    }

    private void InitObject()
    {
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");
        userInfoSQLiteHelper = new UserInfoSQLiteHelper(this);
        //从本地数据库中获取用户信息
        userInfo = userInfoSQLiteHelper.getUserInfo(userEmail);
    }

    private void InitView()
    {
        userNameTextView = (TextView) findViewById(R.id.personal_info_detail_user_name);
        userEmailTextView = (TextView) findViewById(R.id.personal_info_detail_user_email);
        userIntegralTextView = (TextView) findViewById(R.id.personal_info_detail_user_integral);
        userSignatureTextView = (TextView) findViewById(R.id.personal_information_detail_user_signature);
    }

    private void InitListener()
    {
        userNameTextView.setOnClickListener(this);
        userEmailTextView.setOnClickListener(this);
        userIntegralTextView.setOnClickListener(this);
        userSignatureTextView.setOnClickListener(this);

    }

    private void InitTextView()
    {
        userNameTextView.setText(userInfo.getUserName());
        userEmailTextView.setText(userInfo.getUserEmail());
        userIntegralTextView.setText(String.valueOf(userInfo.getUserIntegral()));
        if(userInfo.getUserSignature() != null)
            userSignatureTextView.setText(userInfo.getUserSignature());
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.personal_info_detail_user_name:
                Intent intent = new Intent(PersonalInformationDetail.this, PersonalInformationUpdateUserName.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivityForResult(intent, 1);
                Toast.makeText(this, "点击了用户名", Toast.LENGTH_SHORT).show();
            case R.id.personal_info_detail_user_email:
                Toast.makeText(this, "点击了邮箱", Toast.LENGTH_SHORT).show();

        }
    }

    //其他Activity返回的数据处理
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == 1)
            {
                userInfo.setUserName(data.getStringExtra("userName"));
                if(userInfoSQLiteHelper.updateUserInfo(userInfo))
                {
                    InitTextView();
                }
                else
                {
                    Toast.makeText(this, "更新用户信息失败", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}


