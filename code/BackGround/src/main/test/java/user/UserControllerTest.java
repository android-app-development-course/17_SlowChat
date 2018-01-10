package user;

import com.chat.controller.UserController;
import com.chat.entity.Tag;
import com.chat.entity.User;
import com.chat.util.SpringUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserControllerTest {
    public static void main(String[] args) {
        getUser();
    }

    //用email获取user信息
    public static void getUser(){
        UserController controller= SpringUtil.getBean(UserController.class);
        User user=controller.getUserMessage("729164860@qq.com");
        System.out.println(user);
    }

    //用email获取userTags
    public static void getTags(){
        UserController controller=SpringUtil.getBean(UserController.class);
        Set<Tag> set=controller.getUserTags("729164860@qq.com");
        for(Tag tag:set){
            System.out.println(tag.getName());
        }
    }

    //修改userTags
    public static void setTags(){
        UserController controller=SpringUtil.getBean(UserController.class);
        String[] strings={"吃西瓜","吃荔枝"};
        Map map=controller.setUserTags("729164860@qq.com",strings);
    }

}
