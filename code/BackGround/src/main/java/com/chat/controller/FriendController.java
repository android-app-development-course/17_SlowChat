package com.chat.controller;

import com.chat.entity.User;
import com.chat.service.friend.FriendService;
import com.chat.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class FriendController {
    @Autowired
    FriendService friendService;

    /**
     * 获取好友申请列表
     * @param email 邮箱
     * @return 好友申请
     */
    @RequestMapping(value = "/user/getApplyFriends.do",method = RequestMethod.GET)
    @ResponseBody
    public Set<User> getApplyFriends(String email){
        return friendService.getFriendApply(email);
    }

    /**
     * 发送好友申请
     * @param sponsorEmail 发送者邮箱
     * @param reciverEmail 目标邮箱
     * @return 相关信息
     */
    @RequestMapping(value = "/user/addFriend.do",method = RequestMethod.GET)
    @ResponseBody
    public Map addFriend(String sponsorEmail, String reciverEmail){
        friendService.sponsorFriend(sponsorEmail,reciverEmail);

        return ResponseUtil.getSuccessResponse();
    }

    /**
     * 接受好友申请
     * @param sponsorEmail 好友申请发送者
     * @param reciverEmail 好友申请接受者
     * @return 状态信息
     */
    @RequestMapping(value = "/user/acceptFriend.do",method = RequestMethod.GET)
    @ResponseBody
    public Map acceptFriend(String sponsorEmail,String reciverEmail){
        if(friendService.acceptFriend(sponsorEmail,reciverEmail)){
            return ResponseUtil.getSuccessResponse();
        }else {
            return ResponseUtil.getFailureResponse("好友接受失败，请检查传递参数是否有空值！");
        }
    }

    /**
     * 删除好友申请
     * @param sponsorEmail 好友申请发送者
     * @param reciverEmail 好友申请接受者
     * @return 状态信息
     */
    @RequestMapping(value = "/user/deleteFriend.do",method = RequestMethod.GET)
    @ResponseBody
    public Map deleteFriend(String sponsorEmail,String reciverEmail){
        if(friendService.deleteFriend(sponsorEmail,reciverEmail)){
            return ResponseUtil.getSuccessResponse();
        }else {
            return ResponseUtil.getFailureResponse("好友删除失败，请检查传递参数是否有空值！");
        }
    }
}
