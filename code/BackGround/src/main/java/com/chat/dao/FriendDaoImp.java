package com.chat.dao;

import com.chat.entity.Friend;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class FriendDaoImp implements FriendDao{

    public Friend getFriend(User user, User friend) {
        Session session= HibernateUtil.getCurrentSession();
        Query<Friend> query=session.createQuery(
                "from Friend where User =:user and Friend =:friend",Friend.class);
        query.setParameter("user",user);
        query.setParameter("friend",friend);

        return query.uniqueResult();
    }

    public void addFriend(Friend friend) {

    }

    public void deleteFriend(Friend friend) {

    }

    public void updataFriend(Friend friend) {

    }
}
