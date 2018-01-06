package com.chat.dao;


import com.chat.entity.Tag;
import com.chat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDaoImp implements TagDao{
    public Tag getTag(String tagName) {
        Session session=HibernateUtil.getCurrentSession();
        Query<Tag> query= session.createQuery("from Tag where name=?",Tag.class);
        query.setParameter(0,tagName);

        return query.uniqueResult();
    }

    public void addTag(Tag tag) {
        Session session=HibernateUtil.getCurrentSession();
        session.save(tag);
    }
}
