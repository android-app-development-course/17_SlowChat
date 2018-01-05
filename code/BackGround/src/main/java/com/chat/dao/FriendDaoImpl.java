package com.chat.dao;

import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class FriendDaoImpl implements FriendDao{
    public int getFeeling(User user, User friend) {
        Session session= HibernateUtil.getCurrentSession();

        return 0;
    }

    public short getSendCount(User user, User friend) {
        return 0;
    }

    public void setFeeling(User user, User friend, int feeling) {

    }

    public void setSendCount(User user, User friend, short count) {

    }
}
