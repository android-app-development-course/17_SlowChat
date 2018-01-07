package com.chat.service.friend;

import com.chat.entity.Friend;
import com.chat.entity.User;

import java.util.List;
import java.util.Set;

public interface FriendService {
    /**
     * 发送好友申请
     * @param sponsorEmail 发送者
     * @param reciverEmail 接受者
     * @return 是否成功
     */
    boolean sponsorFriend(String sponsorEmail, String reciverEmail);

    /**
     * 接收好友申请
     * @param sponsorMail 发送者
     * @param reciverMail 接受者
     * @return 是否成功
     */
    boolean acceptFriend(String sponsorMail,String reciverMail);

    /**
     * 删除一名好友
     * @param sponsorMail 发起者
     * @param receiverMail 被删除者
     */
    boolean deleteFriend(String sponsorMail,String receiverMail);

    /**
     * 获取待接收的好友申请
     * @param email 用户邮箱
     * @return 待接收的好友集合，普通的java对象
     */
    Set<User> getFriendApply(String email);
}
