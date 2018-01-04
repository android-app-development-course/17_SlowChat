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
     * 修改用户信息
     * @param username 用户名
     * @param signature 用户签名
     * @param email 用户邮箱
     * @return 是否插入成功
     */
    boolean setUserMessage(String username, String signature, String email);

    /**
     * 用户进行签到
     * @param email 要签到的用户邮箱
     * @return true代表签到成功，false代表签到失败
     */
    boolean signIn(String email);

    /**
     * 获取用户好友
     */
}
