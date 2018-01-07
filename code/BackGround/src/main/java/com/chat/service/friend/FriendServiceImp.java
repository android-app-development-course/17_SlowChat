package com.chat.service.friend;

import com.chat.dao.UserDao;
import com.chat.entity.Friend;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FriendServiceImp implements FriendService{
    @Autowired
    private UserDao userDao;

    public boolean acceptFriend(User sponsor, User reciver) {
        //如果好友申请不在好友申请表中则不能加好友
        Set<User> set=reciver.getAcceptFriends();
        if(!set.contains(sponsor)) return false;

        //互相加好友
        set.remove(sponsor);

        Friend friend= SpringUtil.getBean(Friend.class);

        friend.setUser(sponsor);
        friend.setFriend(reciver);

        return true;
    }

    public void sponsorFriend(User sponsor, User reciver) {
        reciver.getAcceptFriends().add(sponsor);
    }

    public void deleteFriend(User sponsor, User receiver) {
        sponsor.getFriends().remove(receiver);
        receiver.getFriends().remove(sponsor);
    }

    public Set<User> getFriendApply(String email) {
        Session session=HibernateUtil.getCurrentSession();
        User user=userDao.getUserByEmail(email);

        Set<User> set=user.getAcceptFriends();

        session.evict(user);

        return set;
    }
}
