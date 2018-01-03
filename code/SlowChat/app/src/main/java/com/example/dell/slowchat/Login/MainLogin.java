package com.example.dell.slowchat.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.slowchat.ChatManage.ChatInterface;
import com.example.dell.slowchat.MainInterface.MainActivity;
import com.example.dell.slowchat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieStore;

public class MainLogin extends AppCompatActivity {
    //控件
    private EditText usernameText;
    private EditText passwordText;

    private Button loginBtn;
    private Button exitBtn;

    //用户
    private UserInfo userInfo;

    //对象
    private UserInfoSQLiteHelper userInfoSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        initial();
        clickExit();
        clickLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login_action_settings) {
            Toast.makeText(this, getString(R.string.login_toolbar_setting), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.login_action_register)
        {
            Intent intent = new Intent(MainLogin.this, Register.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.login_action_find_back_password)
        {
            Toast.makeText(this, getString(R.string.login_toolbar_find_back_password), Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initial(){

        this.usernameText=(EditText) findViewById(R.id.login_username_input);
        this.passwordText=(EditText)findViewById(R.id.login_password_input);

        this.loginBtn=(Button)findViewById(R.id.login_login);
        this.exitBtn=(Button)findViewById(R.id.login_exit);

        this.userInfoSQLiteHelper = new UserInfoSQLiteHelper(this);

    }

    private void clickLogin(){
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });
    }
    //本地以及在线两种方式登录
    private void login()
    {
        if(loginLocal())
        {
            sentUserEmail();
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setClass(MainLogin.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT);
        }
        else
        {
            loginOnline();
        }
    }

    //连接本地数据库登录
    private boolean loginLocal()
    {
        String tempUserEmail = usernameText.getText().toString();
        String tempUserPassword = passwordText.getText().toString();
        if( tempUserEmail.length() == 0 || tempUserPassword.length() ==0 )
            return false;
        switch (userInfoSQLiteHelper.checkPassword(tempUserEmail, tempUserPassword))
        {
            case 0:
                return false;
            case 2:
                return false;
        }
        return true;
    }


    //连接服务器端数据库登录
    private void loginOnline()
    {
        String path = "http://119.29.190.214/login/login.do";
        //创建AsyncHttpClient实例
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams requestParams = new RequestParams();
        requestParams.put("email",usernameText.getText().toString());
        requestParams.put("pwd", passwordText.getText().toString());
        client.setTimeout(5000);
        client.get(path, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                JsonParse jsonParse = new JsonParse();
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
                sentUserEmail();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(MainLogin.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT);
                break;
            case 1:
                Toast.makeText(this, "账号不存在或密码错误", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //连接服务器失败调用
    private void connectFail()
    {
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    private void sentUserEmail()
    {
        //获取SharePreferences对象，参数表示文件名，MODE_PRIVATE表示文件操作模式
        SharedPreferences sharedPreferences = getSharedPreferences("data", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit(); //获取编辑器
        editor.putString("userEmail", usernameText.getText().toString());
        editor.commit();
    }

    private void clickExit(){
        this.exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainLogin.this.ifExit();
            }
        });
    }

    private void ifExit(){
        String exitQuest=MainLogin.this.getString(R.string.login_dialog_exit);
        new AlertDialog.Builder(MainLogin.this)
                .setTitle(MainLogin.this.getString(R.string.login_dialog_tip))
                .setMessage(exitQuest)
                .setPositiveButton(MainLogin.this.getString(R.string.login_dialog_sure) ,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                System.exit(0);
                            }
                        }
                )
                .setNegativeButton(MainLogin.this.getString(R.string.login_dialog_cancel) ,null)
                .show();
    }

}
