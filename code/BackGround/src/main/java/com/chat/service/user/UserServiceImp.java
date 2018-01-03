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

    public User getUser(String email) {
        User user=userDao.getUserByEmail(email);
        if (user==null){
            return null;
        }
        HibernateUtil.getCurrentSession().delete(user);
        user.setPwd(null);
        return user;
    }

    public boolean signIn(User user) {
        if(user.getStatus()!=0){
            return false;
        }

        user.setStatus(new Byte("1"));
        user.setIntegral(user.getIntegral()+1);
        userDao.update(user);

        return true;
    }

    public void updataUser(User user) {
        userDao.update(user);
    }
}
