package com.chat.dao;

import com.chat.entity.*;

public interface UserDao {
    /**
     * 添加用户
     * @param user 一个user的实体
     */
    void add(User user);

    /**
     * 根据email查找user
     * @param email user的email
     * @return 返回查找到的实体，若查找不到则返回null
     */
    User getUserByEmail(String email);

    /**
     * 根据id查找user
     * @param id 用户的id
     * @return 返回查找到的实体，若找不到返回null
     */
    User getUser(String id);

    /**
     * 获取去除敏感信息后游离状态的user
     * @param email 用户邮箱
     * @return 返回去除敏感信息后的user
     */
    User getBriefUserByEmail(String email);

    /**
     * 更新用户
     * @param user
     */
    void update(User user);
}
