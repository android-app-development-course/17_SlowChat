package com.chat.service.login;

import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegisterServiceImp implements RegisterService {
    @Autowired
    private UserDao userDao;

    public boolean isEmailFit(String email) {
        final String pattern1 = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);

        return mat.find();
    }

    public boolean isEmailUnregistered(String eamil) {
        User user=userDao.getUserByEmail(eamil);

        return user==null;
    }

    public boolean isPwdFit(String pwd) {
        final String pattern1="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,16}$";
        final Pattern pattern=Pattern.compile(pattern1);
        final Matcher mat=pattern.matcher(pwd);

        return mat.find();
    }

    public void addUser(String email, String pwd) {
        User user= SpringUtil.getBean(User.class);
        user.setEmail(email);
        user.setEmail(pwd);

        userDao.add(user);
    }
}
