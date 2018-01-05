package com.example.dell.slowchat.Login;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.slowchat.R;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    //空间
    private Button registerBtn;
    private EditText userNameEditText;
    private EditText userEmailEditText;
    private EditText userPasswordEditText;
    private EditText userConfirmPasswordEditText;
    private UserInfoSQLiteHelper userInfoSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialView();
        initialListener();
        initialObject();
    }


    //初始化View
    private void initialView()
    {
        this.registerBtn = (Button) findViewById(R.id.register_btn);
        this.userNameEditText = (EditText) findViewById(R.id.register_user_name_editText);
        this.userEmailEditText = (EditText) findViewById(R.id.register_user_email_editText);
        this.userPasswordEditText = (EditText) findViewById(R.id.register_user_password_editText);
        this.userConfirmPasswordEditText = (EditText) findViewById(R.id.register_user_confirm_password_editText);

    }

    //初始化监听器
    private void initialListener()
    {
        this.registerBtn.setOnClickListener(this);
    }

    //初始化对象
    private void initialObject()
    {
        userInfoSQLiteHelper = new UserInfoSQLiteHelper(this);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.register_btn:
                if(checkUserInfo() != 1)
                {
                    return;
                }
                registerOnline(userEmailEditText.getText().toString(),
                       userPasswordEditText.getText().toString(),
                       userNameEditText.getText().toString());
        }
    }

    //在线注册
    private void registerOnline(String email, String password, String name)
    {
        String path = "http://119.29.190.214/login/register.do";
        //设置插入数据库的信息
        final RequestParams requestParams = new RequestParams();
        requestParams.put("email",email);
        requestParams.put("pwd", password);
        requestParams.put("username", name);
        //创建AsyncHttpClient实例
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(path, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                JsonParse jsonParse = new JsonParse();
                String json = new String(bytes);
                System.out.println(json);
                connectSuccess(jsonParse.getRegisterResult(bytes));

            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
            {
                connectFail();
            }

        });

    }

    //连接服务器成功调用
    private void connectSuccess(int result)
    {
        switch (result)
        {
            case 0:
                userInfoSQLiteHelper.updateUser(userEmailEditText.getText().toString(), userPasswordEditText.getText().toString());
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case 1:
                Toast.makeText(this, "注册失败，可能邮箱已被注册", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //连接服务器失败调用
    private void connectFail()
    {
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }


    //判断输入的注册信息是否正确
    private int checkUserInfo()
    {
        if(checkUserName() != 1)
            return 0;
        if(checkUserEmail() != 1)
            return 0;
        if(checkUserPassword() != 1)
            return 0;
        if(checkUserConfirmPassword() != 1)
            return 0;
        return 1;
    }

    //判断输入用户名是否正确
    private int checkUserName()
    {
        String tempUserName = userNameEditText.getText().toString();
        if (tempUserName.length() == 0)
        {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if (userNameEditText.getText().length() > 30)
        {
            Toast.makeText(this, "用户名过长", Toast.LENGTH_SHORT).show();
            return 2;
        }
        else
            return 1;
    }

    private int checkUserEmail()
    {
        String tempUserEmail = userEmailEditText.getText().toString();
        //采用正则表达式判断邮箱格式是否正确
        String email = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern regex = Pattern.compile(email);
        Matcher matcher = regex.matcher(tempUserEmail);
        if(matcher.matches())
        {
            if(tempUserEmail.length() > 70)
            {
                Toast.makeText(this, "邮箱过长", Toast.LENGTH_SHORT).show();
                return 2;
            }
            else
            {
                if(userInfoSQLiteHelper.checkUser(tempUserEmail))
                {
                    Toast.makeText(this, "邮箱已被注册", Toast.LENGTH_SHORT).show();
                    return 0;
                }
                else
                {
                    return 1;
                }
            }

        }
        Toast.makeText(this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
        return 0;
    }

    //检测密码是否符合要求
    private int checkUserPassword()
    {
        String tempUserPassword = userPasswordEditText.getText().toString();
        //采用正则表达式判断密码格式是否正确
        String password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,16}$";
        Pattern regex = Pattern.compile(password);
        Matcher matcher = regex.matcher(tempUserPassword);
        if(matcher.matches())
        {
            return 1;
        }
        else
        {
            Toast.makeText(this, "密码长度为8~16位数字和字母组合", Toast.LENGTH_SHORT).show();
            return 0;
        }

    }

    //检测确认密码与原密码是否相符
    private int checkUserConfirmPassword()
    {
        String tempUserConfirmPassword = userConfirmPasswordEditText.getText().toString();
        if(tempUserConfirmPassword.equals(userPasswordEditText.getText().toString()))
            return 1;
        else
        {
            Toast.makeText(this, "确认密码与原密码不相符", Toast.LENGTH_SHORT).show();
            return 0;
        }

    }
}
