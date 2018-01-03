package com.chat.dao;

import com.chat.entity.Friend;
import com.chat.entity.User;

import java.util.List;

public interface FriendDao {
    /**
     * 获取好友列表
     * @param user 需要获取好友列表的用户
     * @return 返回好友列表
     */
    List<Friend> friendList(User user);


}
