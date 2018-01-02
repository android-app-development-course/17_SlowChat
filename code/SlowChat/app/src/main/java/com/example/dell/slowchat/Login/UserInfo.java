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

    UserInfo(int userId, String userName, String userEmail, String userPassword, Drawable userImage)
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
    private void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    //设置用户密码
    private void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    //设置用户头像
    private void setUserImage(Drawable userImage)
    {
        this.userImage = userImage;
    }

    //设置用户个性签名
    private void setUserSignature(String userSignature)
    {
        this.userSignature = userSignature;
    }

    //设置用户经验值
    private void setUserIntegral(int userIntegral)
    {
        this.userIntegral = userIntegral;
    }

    //设置用户签到状态
    private void setUserStatus(int userStatus)
    {
        this.userStatus = userStatus;
    }

    //设置用户登录状态
    private void setUserState(int userState)
    {
        this.userState = userState;
    }

    //设置用户ID
    public int setUserId()
    {
        return this.userId;
    }

    //设置用户名
    public String setUserName()
    {
        return this.userName;
    }

    //设置用户邮箱
    private String setUserEmail()
    {
        return this.userEmail;
    }

    //设置用户密码
    private String setUserPassword()
    {
        return this.userPassword;
    }

    //设置用户头像
    private Drawable setUserImage()
    {
        return this.userImage;
    }

    //设置用户个性签名
    private String setUserSignature()
    {
        return this.userSignature;
    }

    //设置用户经验值
    private int setUserIntegral()
    {
        return this.userIntegral;
    }

    //设置用户签到状态
    private int setUserStatus()
    {
        return this.userStatus;
    }

    //设置用户登录状态
    private int setUserState()
    {
        return this.userState;
    }

}
