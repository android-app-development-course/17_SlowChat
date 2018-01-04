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
        User user=userDao.getUserByEmail(email);
        if (user==null) return false;
        Set<Tag> tags=user.getTags();
        tags.clear();

        System.out.println(tags.size());

        for(String tagName:tagsName){
            Tag tag=tagDao.getTag(tagName);
            System.out.println(tagName);
            if(tag==null){
                tag= SpringUtil.getBean(Tag.class);
                tag.setName(tagName);
                tagDao.addTag(tag);

                System.out.println(tagName);
            }

            tags.add(tag);
            HibernateUtil.getCurrentSession().getTransaction().commit();
            HibernateUtil.getCurrentSession().beginTransaction();
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
