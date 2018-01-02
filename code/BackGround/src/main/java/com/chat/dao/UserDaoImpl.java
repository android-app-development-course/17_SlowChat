package com.chat.dao;

import com.chat.entity.User;
import com.chat.util.HibernateUtil;


public class UserDaoImpl implements UserDao{
    public void add(User user) {
        HibernateUtil.getSession().saveOrUpdate(user);
    }

    public void update(User user) {
        HibernateUtil.getSession().saveOrUpdate(user);
    }

    public User getUser(String id) {

        return null;
    }

    public User getUserByEmail(String email) {
        return null;
    }
}
