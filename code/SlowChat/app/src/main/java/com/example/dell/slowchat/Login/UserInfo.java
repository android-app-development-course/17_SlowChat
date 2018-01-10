package com.example.dell.slowchat.Login;

import android.graphics.drawable.Drawable;

/**
 * Created by you on 2018/1/1.
 */

public class UserInfo {

    //用户信息
    private int userId;            //用户ID
    private String userName;       //用户名
    private String userEmail;      //用户邮箱
    private String userPassword;   //用户密码
    private Drawable userImage;    //用户头像
    private String userSignature;  //用户签名
    private int userIntegral;      //用户经验值
    private int userStatus;        //用户签到状态
    private int userState;         //用户登录状态

    UserInfo()
    {

    }

    UserInfo(String userName, String userEmail, String userPassword, Drawable userImage)
    {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userImage = userImage;
        this.userSignature = null;
        this.userIntegral = 0;
        this.userStatus = 0;
        this.userState = 0;
    }

    //设置用户基本信息（无密码）
    public void setUserInfo(int userId, String userName, String userEmail, String userSignature,
    int userIntegral, int userStatus)
    {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSignature = userSignature;
        this.userIntegral = userIntegral;
        this.userStatus = userStatus;
    }

    //设置用户ID
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    //设置用户名
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    //设置用户邮箱
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    //设置用户密码
    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    //设置用户头像
    public void setUserImage(Drawable userImage)
    {
        this.userImage = userImage;
    }

    //设置用户个性签名
    public void setUserSignature(String userSignature)
    {
        this.userSignature = userSignature;
    }

    //设置用户经验值
    public void setUserIntegral(int userIntegral)
    {
        this.userIntegral = userIntegral;
    }

    //设置用户签到状态
    public void setUserStatus(int userStatus)
    {
        this.userStatus = userStatus;
    }

    //设置用户登录状态
    public void setUserState(int userState)
    {
        this.userState = userState;
    }


    //获取用户ID
    public int getUserId()
    {
        return this.userId;
    }

    //获取用户名
    public String getUserName()
    {
        return this.userName;
    }

    //获取用户邮箱
    public String getUserEmail()
    {
        return this.userEmail;
    }

    //获取用户密码
    public String getUserPassword()
    {
        return this.userPassword;
    }

    //获取用户头像
    public Drawable getUserImage()
    {
        return this.userImage;
    }

    //获取用户个性签名
    public String getUserSignature()
    {
        return this.userSignature;
    }

    //获取用户经验值
    public int getUserIntegral()
    {
        return this.userIntegral;
    }

    //获取用户签到状态
    public int getUserStatus()
    {
        return this.userStatus;
    }

    //获取用户登录状态
    public int getUserState()
    {
        return this.userState;
    }

    public String toString()
    {
        return userId + "," + userName + "," + userEmail + "," + userPassword + "," + userImage+ ","
                +userSignature + "," + userIntegral + "," + userStatus+ "," +userState;
    }

}
