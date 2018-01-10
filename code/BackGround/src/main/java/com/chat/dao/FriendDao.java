package com.chat.dao;

import com.chat.entity.Friend;
import com.chat.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDao {
    /**
     * 获取Friend对象
     * @param user
     * @param friend
     * @return 返回friend对象
     */
    Friend getFriend(User user,User friend);

    /**
     * 删除Friend对象
     * @param friend 好友对象
     */
    void deleteFriend(Friend friend);

    /**
     * 更新好友对象
     * @param friend 好友对象
     */
    void updataFriend(Friend friend);

    /**
     * 插入好友对象
     * @param friend 好友对象
     */
    void addFriend(Friend friend);
}
