package user;

import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;

public class UserDaoTest {
    private static UserDao userDao=SpringUtil.getBean(UserDao.class);

    public static void main(String[] args) {
        HibernateUtil.getCurrentSession().beginTransaction();

        getUserByEmail();

        HibernateUtil.getCurrentSession().getTransaction().commit();
    }

    //添加一个用户
    private static void addUser(){
        User user=new User();
        user.setEmail("2sda1ds123df");
        user.setPwd("pwd231");
        user.setUsername("v");
        userDao.add(user);
    }

    //查找一个用户
    private static void getUser(){
        System.out.println(userDao.getUser("14"));
    }

    //按照邮箱查找一个用户
    private static void getUserByEmail(){
        User user=userDao.getBriefUserByEmail("729164860@qq.com");
        System.out.println(user);
        System.out.println(user.getFriends());
        System.out.println(user.getTags());
        System.out.println(user.getFriendApplies());
        System.out.println(user.getMessages());
    }

    //更新一个User
    private static void updataUser(){
        User user=userDao.getUser("14");
        user.setEmail("25310@pp.com");
        userDao.update(user);
    }
}
