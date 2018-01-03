package user;

import com.chat.controller.UserController;
import com.chat.entity.User;
import com.chat.util.SpringUtil;

public class UserControllerTest {
    public static void main(String[] args) {
        getUser();
    }

    //用email获取user信息
    public static void getUser(){
        UserController controller= SpringUtil.getBean(UserController.class);
        User user=controller.getUser("729164860@qq.com");
        System.out.println(user);
    }
}
