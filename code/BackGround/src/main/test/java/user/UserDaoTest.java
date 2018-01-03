package user;

import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;

public class UserDaoTest {
    private static UserDao userDao=SpringUtil.getBean(UserDao.class);

    public static void main(String[] args) {
        HibernateUtil.getCurrentSession().beginTransaction();

        updataUser();

        HibernateUtil.getCurrentSession().getTransaction().commit();
    }

    //添加一个用户
    private static void addUser(){
        User user=new User();
        user.setEmail("729164860@qq.com");
        user.setPwd("pwd");
        userDao.add(user);
    }

    //查找一个用户
    private static void getUser(){
        System.out.println(userDao.getUser("14"));
    }

    //按照邮箱查找一个用户
    private static void getUserByEmail(){
        System.out.println(userDao.getUserByEmail("729164860@qq.com"));
    }

    //更新一个User
    private static void updataUser(){
        User user=userDao.getUser("14");
        user.setEmail("25310@pp.com");
        userDao.update(user);
    }
}
