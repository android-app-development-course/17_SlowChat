package user;

import com.chat.dao.FriendDao;
import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.service.friend.FriendService;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class FriendServiceTest {
    public static void main(String[] args) {
        HibernateUtil.getCurrentSession().beginTransaction();
        Set<User> set=getAcceptFriends();
        UserDao dao=SpringUtil.getBean(UserDao.class);
        User user=dao.getUserByEmail("2535109853@qq.com");
        set.add(user);

        HibernateUtil.getCurrentSession().getTransaction().commit();
        System.out.println(set.size());
    }

    //测试是否能获取朋友申请列表
    public static Set<User> getAcceptFriends(){
        FriendService service=SpringUtil.getBean(FriendService.class);
        Set<User> list=service.getFriendApply("729164860@qq.com");

        return list;
    }
}
