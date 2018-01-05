package com.chat.service.tag;

import com.chat.dao.TagDao;
import com.chat.dao.UserDao;
import com.chat.entity.Tag;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImp implements TagService{
    @Autowired
    UserDao userDao;

    @Autowired
    TagDao tagDao;

    public boolean setUserTags(String email, String[] tagsName) {
        //判断user是否为空并清楚tag集
        User user=userDao.getUserByEmail(email);
        if (user==null) return false;
        Set<Tag> tags=user.getTags();
        tags.clear();

        for(String tagName:tagsName){
            Tag tag=tagDao.getTag(tagName);

            if(tag==null){
                //加入tag
                tag= SpringUtil.getBean(Tag.class);
                tag.setName(tagName);
                tagDao.addTag(tag);
            }

            tags.add(tag);
        }

        return true;
    }

    public Set<Tag> getUserTags(String email) {
        User user=userDao.getUserByEmail(email);
        if(user==null){
            return null;
        }

        //手动加载tags
        user.getTags().size();

        return user.getTags();
    }
}
