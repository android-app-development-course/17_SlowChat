package com.example.dell.slowchat.PersonalInformation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.slowchat.Login.UserInfoSQLiteHelper;
import com.example.dell.slowchat.R;

public class PersonalInformationUpdateUserName extends Activity implements View.OnClickListener{

    private String userName;
    private EditText userNameUserEditText;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_update_user_name);


        initView();
        initListener();
        initObject();
    }

    private void initView()
    {
        userNameUserEditText = (EditText) findViewById(R.id.personal_info_update_user_name);
        confirmBtn = (Button) findViewById(R.id.personal_info_update_confirm_btn);
    }

    private void initListener()
    {
        userNameUserEditText.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }

    private void initObject()
    {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userNameUserEditText.setText(userName);
    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.personal_info_update_confirm_btn:
                clickConfirmBtn();
                Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickConfirmBtn()
    {
        Intent intent = new Intent();
        intent.putExtra("userName", userNameUserEditText.getText().toString());
        setResult(1, intent);
        finish();
    }

}
