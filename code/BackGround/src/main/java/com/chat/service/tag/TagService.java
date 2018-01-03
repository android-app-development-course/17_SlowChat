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
     * 给用户添加一个标签
     * @param email 用户邮箱
     * @param tagName 标签名字
     * @return 是否添加成功
     */
    boolean addTag(String email,String tagName);
}
