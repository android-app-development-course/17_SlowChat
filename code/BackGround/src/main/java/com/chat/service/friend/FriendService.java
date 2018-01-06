package com.chat.service.friend;

import com.chat.entity.User;

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

}
