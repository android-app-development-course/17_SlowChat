package com.example.dell.slowchat.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.slowchat.MainInterface.MainActivity;
import com.example.dell.slowchat.R;

public class MainLogin extends AppCompatActivity {
    private String username;
    private String password;

    private EditText usernameText;
    private EditText passwordText;


    private Button loginBtn;
    private Button exitBtn;

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initial(){
        this.username="admin";
        this.password="admin";

        this.usernameText=(EditText) findViewById(R.id.login_username_input);
        this.passwordText=(EditText)findViewById(R.id.login_password_input);

        this.loginBtn=(Button)findViewById(R.id.login_login);
        this.exitBtn=(Button)findViewById(R.id.login_exit);
    }

    private void clickLogin(){
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(MainLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
