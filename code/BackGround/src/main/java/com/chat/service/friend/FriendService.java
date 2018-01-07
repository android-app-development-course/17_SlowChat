package com.chat.service.friend;

import com.chat.entity.Friend;
import com.chat.entity.User;

import java.util.List;
import java.util.Set;

public interface FriendService {
    /**
     * 发送好友申请
     * @param sponsor 发送者
     * @param reciver 接受者
     * @return 是否成功
     */
    void sponsorFriend(User sponsor, User reciver);

    /**
     * 接收好友申请
     * @param sponsor 发送者
     * @param reciver 接受者
     * @return 是否成功
     */
    boolean acceptFriend(User sponsor,User reciver);

    /**
     * 删除一名好友
     * @param sponsor 发起者
     * @param receiver 被删除者
     */
    void deleteFriend(User sponsor,User receiver);

    /**
     * 获取待接收的好友申请
     * @param email 用户邮箱
     * @return 待接收的好友集合，普通的java对象
     */
    Set<User> getFriendApply(String email);
}
