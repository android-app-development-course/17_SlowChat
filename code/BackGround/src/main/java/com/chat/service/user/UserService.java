package com.chat.service.user;

import com.chat.entity.User;

public interface UserService {
    /**
     * 根据用户邮箱获取用户信息
     * @param email 用户邮箱
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 根据用户ID获取用户信息
     * @param id 用户id
     * @return 用户
     */
    User getUserById(String id);

    /**
     * 更新用户信息
     * 不能传给这个方法一个游离态用户，否则它将会被持久化到数据库中
     * @param user 持久态用户
     */
    void updataUser(User user);
}
