package com.chat.service.user;

import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserDao userDao;

    public User getUserMessage(String email) {
        User user=userDao.getBriefUserByEmail(email);

        if (user==null){
            return null;
        }

        return user;
    }

    public boolean signIn(String email) {
        User user=userDao.getUserByEmail(email);
        if(user==null){
            return false;
        }

        if(user.getStatus()!=0){
            return false;
        }

        //把签到状态设置为1且积分+1
        user.setStatus(new Byte("1"));
        user.setIntegral(user.getIntegral()+1);
        userDao.update(user);

        return true;
    }

    public boolean setUserMessage(String username, String signature, String email) {
        User user=userDao.getUserByEmail(email);

        if (user==null){
            return false;
        }

        if(signature!=null){
            user.setSignature(signature);
        }

        if (username!=null){
            user.setUsername(username);
        }

        return true;
    }
}
