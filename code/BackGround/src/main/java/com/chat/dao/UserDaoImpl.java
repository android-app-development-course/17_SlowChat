package com.chat.dao;

import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
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

        return query.uniqueResult();
    }

    public User getBriefUserByEmail(String email) {
        Session session=HibernateUtil.getCurrentSession();
        Query<User> query=session.createQuery(
                "select new User (id,email,signature,integral,status,img,username)" +
                        "from User where email=:email",User.class);
        query.setParameter("email",email);

        return query.uniqueResult();
    }
}
