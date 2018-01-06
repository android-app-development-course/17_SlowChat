package com.example.dell.slowchat.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

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
    private static final String COLUMN_USERINFO_USERID = "userID";
    private static final String COLUMN_USERINFO_USERNAME = "userName";
    private static final String COLUMN_USERINFO_USEREMAIL = "userEmail";
    private static final String COLUMN_USERINFO_USERPASSWORD = "userPassword";
    private static final String COLUMN_USERINFO_USERIMAGE = "userImage";
    private static final String COLUMN_USERINFO_USERSIGNATURE = "userSignature";
    private static final String COLUMN_USERINFO_USERINTEGRAL = "userIntegral";
    private static final String COLUMN_USERINFO_USERSTATUS = "userStatus";
    private static final String COLUMN_USERINFO_USERSTATE = "userState";

    // create table sql query
    private String CREATE_USERINFO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + COLUMN_USERINFO_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERINFO_USERNAME + " VARCHAR(30),"
            + COLUMN_USERINFO_USEREMAIL + " VARCHAR(70) UNIQUE,"
            + COLUMN_USERINFO_USERPASSWORD + " VARCHAR(20),"
            + COLUMN_USERINFO_USERIMAGE + " BLOB,"
            + COLUMN_USERINFO_USERSIGNATURE + " VARCHAR(100),"
            + COLUMN_USERINFO_USERINTEGRAL + " INTEGER,"
            + COLUMN_USERINFO_USERSTATUS + " INTEGER,"
            + COLUMN_USERINFO_USERSTATE + " INTEGER" + ")";

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

//    /**
//     * This method to check user exist or not
//     *
//     * @param email
//     * @return true/false
//     */
//    public boolean checkUser(String email) {
//
//        // array of columns to fetch
//        String[] columns = {COLUMN_USERINFO_USERID};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // selection criteria
//        String selection = COLUMN_USERINFO_USEREMAIL + " = ?";
//
//        // selection argument
//        String[] selectionArgs = {email};
//
//        // query user table with condition
//        /**
//         * Here query function is used to fetch records from user table this function works like we use sql query.
//         * SQL query equivalent to this query function is
//         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
//         */
//        Cursor cursor = db.query(TABLE_NAME, //Table to query
//                columns,                    //columns to return
//                selection,                  //columns for the WHERE clause
//                selectionArgs,              //The values for the WHERE clause
//                null,                       //group the rows
//                null,                      //filter by row groups
//                null);                      //The sort order
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//        if (cursorCount > 0) {
//            return true;
//        }
//        return false;
//    }

    /**
     * This method to check user exist or not locally
     *
     * @param email
     * @return true/false
     */
    public boolean checkUserLocal(String email)
    {
        // array of columns to fetch
        String[] columns = {COLUMN_USERINFO_USERID};
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USERINFO_USEREMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


//    /**
//     * This method to check user exist or not online
//     *
//     * @param email
//     * @return true/false
//     */
//    public boolean checkUserOnline(String email)
//    {
//        return false;
//    }


    /**
     * This method to check password right or not locally
     *
     * @param email
     * @return true/false
     */
    public int checkPassword(String email, String password)
    {
        // array of columns to fetch
        String[] columns = {COLUMN_USERINFO_USERPASSWORD};
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USERINFO_USEREMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // 账号、密码查询情况的标志，返回值
        int flag;

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        if (cursorCount > 0)
        {
            cursor.moveToFirst();
            String tempPassword = cursor.getString(0);
            System.out.println(tempPassword);
            if(tempPassword.equals(password))
            {
                flag = 1;       //账号存在密码正确
            }
            else
            {
                flag = 2;       //账户存在但是密码错误
            }
        }
        else
        {
            flag = 0;           //账号不存在
        }
        cursor.close();
        db.close();
        return flag;
    }


    /**
     * This method to get user information in local database
     *
     * @param email
     * @return true/false
     */
    public UserInfo getUserInfoLocal(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        UserInfo userInfo = new UserInfo();
        // array of columns to fetch
        String[] columns = {COLUMN_USERINFO_USERID,
                COLUMN_USERINFO_USERNAME,
                COLUMN_USERINFO_USEREMAIL,
                COLUMN_USERINFO_USERINTEGRAL,
                COLUMN_USERINFO_USERSIGNATURE};
        // selection criteria
        String selection = COLUMN_USERINFO_USEREMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        cursor.moveToFirst();
        userInfo.setUserId(cursor.getInt(0));
        userInfo.setUserName(cursor.getString(1));
        userInfo.setUserEmail(cursor.getString(2));
        userInfo.setUserIntegral(cursor.getInt(3));
        userInfo.setUserSignature(cursor.getString(4));
        cursor.close();
        db.close();
        return  userInfo;
    }


    /**
     * This method is to create user record in local database
     *把登录的用户更新到本地数据库中，方便查找
     * @param email password
     */
    public void updateUserLocal(final String email, final String password)
    {
        String path = "http://119.29.190.214/user/getUserMessage.do";
        //设置插入数据库的信息
        final RequestParams requestParams = new RequestParams();
        requestParams.put("email",email);
        //创建AsyncHttpClient实例
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(path, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                String json = new String(bytes);
                System.out.print(json);
                JsonParse jsonParse = new JsonParse();
                UserInfo userInfo = jsonParse.getUserInfo(json);
                userInfo.setUserPassword(password);
                if(checkUserLocal(email))
                {
                    if(updateUserInfoLocal(userInfo))
                    {
                        System.out.println("更新用户信息到本地数据库成功");
                    }
                }
                else
                {
                    if(addUserLocal(userInfo))
                    {
                        System.out.println("添加用户信息到本地数据库成功");
                    }
                }

            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
            {
                System.out.println("网络连接失败");
            }
        });
    }

    /**
     * This method is to create user record in local database
     *在本地数据库添加一条用户信息
     * @param userInfo
     */
    public boolean addUserLocal(UserInfo userInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERINFO_USERID, userInfo.getUserId());
        values.put(COLUMN_USERINFO_USERNAME, userInfo.getUserName());
        values.put(COLUMN_USERINFO_USEREMAIL, userInfo.getUserEmail());
        values.put(COLUMN_USERINFO_USERPASSWORD, userInfo.getUserPassword());
        values.put(COLUMN_USERINFO_USERINTEGRAL, userInfo.getUserIntegral());

        // Inserting Row
        try
        {
            db.insert(TABLE_NAME, null, values);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            db.close();
        }
        return true;
    }


    public boolean updateUserInfo(UserInfo userInfo)
    {
        if(updateUserInfoLocal(userInfo))
        {
            System.out.println("更新用户信息到本地数据库成功");
            updateUserInfoOnline(userInfo);
            return true;
        }
        else
        {
            System.out.println("更新用户信息到本地数据库失败");
            return false;
        }
    }

    //更新本地数据库中的用户的信息
    public boolean updateUserInfoLocal(UserInfo userInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            String sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_USERINFO_USERNAME + " = ?, "
                    + COLUMN_USERINFO_USEREMAIL + " = ?, "
                    + COLUMN_USERINFO_USERINTEGRAL + " = ?, "
                    + COLUMN_USERINFO_USERSIGNATURE + " = ? WHERE "
                    + COLUMN_USERINFO_USEREMAIL + " = ?";
            db.execSQL(sql, new Object[]{userInfo.getUserName(),
                    userInfo.getUserEmail(),
                    userInfo.getUserIntegral(),
                    userInfo.getUserSignature(),
                    userInfo.getUserEmail()});
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            db.close();
        }
        return true;
    }

    //更新服务器端数据库中的用户信息
    public void updateUserInfoOnline(final UserInfo userInfo)
    {
        String path = "http://119.29.190.214/user/setUserMessage.do";
        //设置插入数据库的信息
        final RequestParams requestParams = new RequestParams();
        requestParams.put("username", userInfo.getUserName());
        requestParams.put("signature", userInfo.getUserSignature());
        requestParams.put("email", userInfo.getUserEmail());
        //创建AsyncHttpClient实例
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(path, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                String json = new String(bytes);
                JsonParse jsonParse = new JsonParse();
                System.out.println(json);
                if(jsonParse.getRegisterResult(json) == 0)
                {
                    System.out.println("更新用户信息到服务器端数据库成功");
                    getUserInfoOnline(userInfo.getUserEmail());
                }
                else
                {
                    System.out.println("更新用户信息到服务器端数据库失败");
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
            {
                System.out.println("网络连接失败");
            }
        });
    }


    /**
     * This method is to get user information online
     *从服务器端获取用户信息
     * @param email
     */
    public void getUserInfoOnline(final String email)
    {
        String path = "http://119.29.190.214/user/getUserMessage.do";
        //设置插入数据库的信息
        final RequestParams requestParams = new RequestParams();
        requestParams.put("email",email);
        //创建AsyncHttpClient实例
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(path, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                String json = new String(bytes);
                System.out.println(json);

            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
            {
                System.out.println("网络连接失败");
            }
        });
    }
}


