package com.chat.dao;

import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;


public class UserDaoImpl implements UserDao{
    @Transactional
    public void add(User user) {
        Session session=HibernateUtil.getCurrentSession();
        session.save(user);
    }

    public void update(User user) {
        Session session=HibernateUtil.getCurrentSession();
        session.update(user);
    }

    public User getUser(String id) {
        Session session=HibernateUtil.getCurrentSession();
        return session.get(User.class,new Short(id));
    }

    public User getUserByEmail(String email) {
        Session session=HibernateUtil.getCurrentSession();
        Query<User> query=session.createQuery(
                "from User where email=?",User.class);
        query.setParameter(0,email);

        List<User> list=query.list();

        if(list.size()==0){
            return null;
        }

        return list.get(0);
    }
}
