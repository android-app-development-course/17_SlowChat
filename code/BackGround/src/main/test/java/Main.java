import com.chat.dao.UserDao;
import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getCurrentSession().beginTransaction();

        UserDao userDao=SpringUtil.getBean(UserDao.class);
        User user=userDao.getUser("14");
        System.out.println(user.getEmail());

        HibernateUtil.getCurrentSession().getTransaction().commit();
    }
}
