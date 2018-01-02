package com.example.dell.slowchat.Login;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by you on 2018/1/1.
 */

public class UserInfoSQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_NAME = "UserInfo";

    // UserInfo table Columns names
    private static final String COLLUMN_USERINFO_USERID = "userID";
    private static final String COLLUMN_USERINFO_USERNAME = "userName";
    private static final String COLLUMN_USERINFO_USEREMAIL = "userEmail";
    private static final String COLLUMN_USERINFO_USERPASSWORD = "userPassword";
    private static final String COLLUMN_USERINFO_USERIMAGE = "userImage";
    private static final String COLLUMN_USERINFO_USERSIGNATURE = "userSignature";
    private static final String COLLUMN_USERINFO_USERINTEGRAL = "userIntegral";
    private static final String COLLUMN_USERINFO_USERSTATUS = "userStatus";
    private static final String COLLUMN_USERINFO_USERSTATE = "userState";

    // create table sql query
    private String CREATE_USERINFO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + COLLUMN_USERINFO_USERID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLLUMN_USERINFO_USERNAME + " VARCHAR(30),"
            + COLLUMN_USERINFO_USEREMAIL + " VARCHAR(50),"
            + COLLUMN_USERINFO_USERPASSWORD + " VARCHAR(20),"
            + COLLUMN_USERINFO_USERIMAGE + " BLOB,"
            + COLLUMN_USERINFO_USERSIGNATURE + " VARCHAR(100)"
            + COLLUMN_USERINFO_USERINTEGRAL + " INTEGER"
            + COLLUMN_USERINFO_USERSTATUS + " INTEGER"
            + COLLUMN_USERINFO_USERSTATE + " INTEGER" + ")";

    private String DROP_USERINFO_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public UserInfoSQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USERINFO_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop User Table if exist
        db.execSQL(DROP_USERINFO_TABLE);

        // Create table again
        onCreate(db);
    }

}
