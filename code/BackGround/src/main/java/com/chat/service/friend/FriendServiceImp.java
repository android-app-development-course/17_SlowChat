package com.chat.service.friend;

import com.chat.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FriendServiceImp implements FriendService{

    public boolean acceptFriend(User sponsor, User reciver) {
        //如果好友申请不在好友申请表中则不能加好友
        Set<User> set=reciver.getFriends();
        if(!set.contains(sponsor)) return false;

        //互相加好友
        set.remove(sponsor);
        sponsor.getFriends().add(reciver);
        reciver.getFriends().add(sponsor);

        return true;
    }

    public void sponsorFriend(User sponsor, User reciver) {
        reciver.getAcceptFriends().add(sponsor);
    }

    public void deleteFriend(User sponsor, User receiver) {
        sponsor.getFriends().remove(receiver);
        receiver.getFriends().remove(sponsor);
    }
}
