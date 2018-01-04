package com.chat.service.tag;

import com.chat.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {

    /**
     * 获取用户兴趣标签
     * @param email 用户邮箱
     * @return 用户兴趣标签集
     */
    Set<Tag> getUserTags(String email);

    /**
     * 更改用户的tags
     * @param email 用户邮箱
     * @param tagsName 标签名字列表
     * @return 是否添加成功
     */
    boolean setUserTags(String email, String[] tagsName);

}
