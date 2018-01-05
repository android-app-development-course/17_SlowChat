package com.chat.dao;

import com.chat.entity.Friend;
import com.chat.entity.User;

import java.util.List;

public interface FriendDao {
    /**
     * 获取好友间的已发送次数
     * @param user 用户
     * @param friend 好友
     * @return 已发送次数
     */
    short getSendCount(User user,User friend);

    /**
     * 修改好友间的已发送次数
     * @param user 用户
     * @param friend 好友
     * @param count 已发送次数
     */
    void setSendCount(User user,User friend,short count);

    /**
     * 获取好友间的好感度
     * @param user 用户
     * @param friend 好友
     * @return 好感度
     */
    int getFeeling(User user,User friend);

    /**
     * 修改好友间的好感度
     * @param user 用户
     * @param friend 好友
     * @param feeling 好感度
     */
    void setFeeling(User user,User friend,int feeling);
}
