package com.example.dell.slowchat.ChatManage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by dell on 2017/11/30.
 */

public class SQLiteOp extends SQLiteOpenHelper {
    public SQLiteOp(Context context)
    {
        super(context,"SlowChat.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE friend(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20),portrait BLOB)");
//        db.execSQL("CREATE TABLE message(id INTEGER PRIMARY KEY AUTOINCREMENT, friend_id VARCHAR(20)," +
//                "content VARCHAR(1000),time datetime )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
