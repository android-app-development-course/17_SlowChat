package user;

import com.chat.service.login.RegisterService;
import com.chat.service.login.RegisterServiceImp;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;

public class loginTest {
    public static void main(String[] args) {
        isEmailUnregister("7dsfa@qq.com");
    }

    private static void isEmailFix(String email){
        RegisterService registerService =new RegisterServiceImp();
        System.out.println(registerService.isEmailFit(email));
    }

    private static void isPwdFix(String pwd){
        RegisterService registerService =new RegisterServiceImp();
        System.out.println(registerService.isPwdFit(pwd));
    }

    private static void isEmailUnregister(String email){
        HibernateUtil.getCurrentSession().beginTransaction();
        RegisterService registerService= SpringUtil.getBean(RegisterService.class);
        System.out.println(registerService.isEmailUnregistered(email));
        HibernateUtil.getCurrentSession().getTransaction().commit();
    }
}
