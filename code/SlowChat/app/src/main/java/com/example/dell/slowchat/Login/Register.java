package com.example.dell.slowchat.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.slowchat.R;

public class Register extends AppCompatActivity {

    //空间
    private Button registerBtn;
    private EditText userNameEditText;
    private EditText userEmailEditText;
    private EditText userPasswordEditText;
    private EditText userConfirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void initial()
    {
        this.registerBtn = (Button) findViewById(R.id.register_btn);
        this.userNameEditText = (EditText) findViewById(R.id.register_user_name_editText);
        this.userEmailEditText = (EditText) findViewById(R.id.register_user_email_editText);
    }
}
