package user;

import com.chat.dao.TagDao;
import com.chat.dao.UserDao;
import com.chat.entity.Tag;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;

public class UserDaoTest {
    private static UserDao userDao=SpringUtil.getBean(UserDao.class);
    private static TagDao tagDao=SpringUtil.getBean(TagDao.class);

    public static void main(String[] args) {
        HibernateUtil.getCurrentSession().beginTransaction();

        Tag tag=SpringUtil.getBean(Tag.class);
        tag.setName("开摩托");
        tagDao.addTag(tag);

        System.out.println(tag.getId());

        User user=userDao.getUserByEmail("729164860@qq.com");
        user.getTags().clear();
        user.getTags().add(tag);

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
        System.out.println(user.getAcceptFriends());
        System.out.println(user.getMessages());
    }

    //更新一个User
    private static void updataUser(){
        User user=userDao.getUser("14");
        user.setEmail("25310@pp.com");
        userDao.update(user);
    }

    //更新tags
    private static void setTags(){
        User user=userDao.getUserByEmail("729164860@qq.com");
        Tag tag=SpringUtil.getBean(Tag.class);
        tag.setName("柔力球");
        HibernateUtil.getCurrentSession().save(tag);
        HibernateUtil.getCurrentSession().flush();

        user.getTags().add(tag);
        for(Tag tag1:user.getTags()){
            System.out.println(tag1.getName());
        }

        userDao.update(user);
    }
}
