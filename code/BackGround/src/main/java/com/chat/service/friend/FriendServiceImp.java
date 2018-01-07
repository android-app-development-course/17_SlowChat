package com.chat.service.friend;

import com.chat.dao.FriendDao;
import com.chat.dao.UserDao;
import com.chat.entity.Friend;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FriendServiceImp implements FriendService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private FriendDao friendDao;

    public boolean acceptFriend(String sponsorEmail, String reciverMail) {
        User sponsor=userDao.getUserByEmail(sponsorEmail);
        User reciver=userDao.getUserByEmail(reciverMail);

        if(sponsor==null||reciver==null) return false;

        //如果好友申请不在好友申请表中则不能加好友
        Set<User> set= reciver.getApplyFriends();
        if(!set.contains(sponsor)) return false;

        //删除好友申请
        set.remove(sponsor);

        Friend friend= SpringUtil.getBean(Friend.class);

        //相互加好友
        friend.setUser(sponsor);
        friend.setFriend(reciver);

        return true;
    }

    public boolean sponsorFriend(String sponsorEmail, String reciverEmail) {
        User sponsor=userDao.getUserByEmail(sponsorEmail);
        User reciver=userDao.getUserByEmail(reciverEmail);

        if(sponsor==null||reciver==null) return false;

        reciver.getApplyFriends().add(sponsor);

        return true;
    }

    public boolean deleteFriend(String sponsorEmail, String receiverEmail) {
        User sponsor=userDao.getUserByEmail(sponsorEmail);
        User reciver=userDao.getUserByEmail(receiverEmail);

        if(sponsor==null||reciver==null) return false;

        Friend friend=friendDao.getFriend(sponsor,reciver);
        sponsor.getFriends().remove(friend);
        reciver.getFriends().remove(friend);

        return true;
    }

    public Set<User> getFriendApply(String email) {
        Session session=HibernateUtil.getCurrentSession();
        User user=userDao.getUserByEmail(email);

        //抽取所需要的内容
        Set<User> applyFriends=user.getApplyFriends();
        Set<User> set=new HashSet<User>();
        for(User u:applyFriends){
            User apply=SpringUtil.getBean(User.class);
            apply.setEmail(u.getEmail());
            apply.setSignature(u.getSignature());
            apply.setUsername(u.getUsername());

            set.add(apply);
        }

        return set;
    }
}
