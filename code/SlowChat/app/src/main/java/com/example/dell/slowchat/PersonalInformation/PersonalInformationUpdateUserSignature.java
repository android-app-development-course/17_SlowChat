package com.example.dell.slowchat.PersonalInformation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.slowchat.R;

public class PersonalInformationUpdateUserSignature extends AppCompatActivity implements View.OnClickListener{

    //声明控件
    private EditText signatureEditText;
    private Button saveButton;

    //声明变量对象
    private String signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_update_user_signature);


        initView();
        initListener();
        initObject();
    }

    //初始化对象
    private void initObject()
    {
        Intent intent = getIntent();
        signature = intent.getStringExtra("userSignature");
        signatureEditText.setText(signature);
    }

    //初始化视图
    private void initView()
    {
        signatureEditText = (EditText) findViewById(R.id.personal_info_update_signature_editText);
        saveButton = (Button) findViewById(R.id.personal_info_update_signature_btn);
    }

    //初始化监听器
    private void initListener()
    {
        saveButton.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.personal_info_update_signature_btn:
                clickSave();
                break;

        }
    }

    private void clickSave()
    {
        Intent intent = new Intent();
        intent.putExtra("userSignature", signatureEditText.getText().toString());
        setResult(2, intent);
        finish();
    }



}
