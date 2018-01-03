package com.chat.service.user;

import com.chat.entity.User;

public interface UserService {
    /**
     * 根据用户邮箱获取用户信息
     * @param email 用户邮箱
     * @return 除去敏感信息后的用户信息
     */
    User getUserMessage(String email);

    /**
     * 更新用户信息
     * 不能传给这个方法一个游离态用户，否则它将会被持久化到数据库中
     * @param user 持久态用户
     */
    void updataUser(User user);

    /**
     * 用户进行签到
     * @param user 持久态用户
     * @return true代表签到成功，false代表签到失败
     */
    boolean signIn(User user);
}
