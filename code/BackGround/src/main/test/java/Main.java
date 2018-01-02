import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        User userEntity=new User();
        userEntity.setEmail("email");
        userEntity.setPwd("pwd");
        userEntity.setIntegral(12);
        userEntity.setStatus(new Byte("0"));

        Session session= HibernateUtil.getSession();
        session.getTransaction().begin();
        session.save(userEntity);
        session.getTransaction().commit();
    }
}
