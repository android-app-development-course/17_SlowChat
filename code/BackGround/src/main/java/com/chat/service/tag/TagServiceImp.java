package com.chat.service.tag;

import com.chat.dao.TagDao;
import com.chat.dao.UserDao;
import com.chat.entity.Tag;
import com.chat.entity.User;
import com.chat.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TagServiceImp implements TagService{
    @Autowired
    UserDao userDao;

    @Autowired
    TagDao tagDao;

    public boolean addTag(String email, String tagName) {
        User user=userDao.getUserByEmail(email);
        if (user==null) return false;

        Tag tag=tagDao.getTag(tagName);
        if(tag==null){
            tag= SpringUtil.getBean(Tag.class);
            tag.setName(tagName);
            tagDao.addTag(tag);
        }

        return true;
    }

    public Set<Tag> getUserTags(String email) {
        User user=userDao.getUserByEmail(email);
        if(user==null){
            return null;
        }

        return user.getTags();
    }
}
