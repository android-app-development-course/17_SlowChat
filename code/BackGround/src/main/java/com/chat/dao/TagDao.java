package com.chat.dao;

import com.chat.entity.Tag;

public interface TagDao {
    /**
     * 根据tag名称获取tag
     * @param tagName tag的名称
     * @return 持久tag对象
     */
    Tag getTag(String tagName);

    /**
     * 新增一个tag
     * @param tag 要新增的tag
     */
    void addTag(Tag tag);
}
