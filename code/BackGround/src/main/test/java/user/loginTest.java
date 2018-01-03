package user;

import com.chat.controller.LoginController;
import com.chat.service.login.RegisterService;
import com.chat.service.login.RegisterServiceImp;
import com.chat.util.HibernateUtil;
import com.chat.util.SpringUtil;

public class loginTest {
    public static void main(String[] args) {
        registerTest();
    }

    private static void isEmailFix(String email) throws InterruptedException {
        RegisterService registerService =new RegisterServiceImp();
        System.out.println(registerService.isEmailFit(email));

        Thread.currentThread().join();
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

    private static void registerTest(){
        LoginController controller=SpringUtil.getBean(LoginController.class);
        controller.register("2535109853@qq.com","s54er15dfff","v");
    }
}
