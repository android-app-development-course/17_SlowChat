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

        //空处理
        if(query.list().size()==0) return null;

        return query.getSingleResult();
    }

    public void addTag(Tag tag) {
        Session session=HibernateUtil.openSession();
        session.beginTransaction();
        session.save(tag);
        session.getTransaction().commit();
        session.close();
    }
}
