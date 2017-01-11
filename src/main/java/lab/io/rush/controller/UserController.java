package lab.io.rush.controller;

import lab.io.rush.dto.Code;
import lab.io.rush.entity.User;
import lab.io.rush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyang on 17/1/4.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;

    @RequestMapping("/userPanel")
    public Map getUserPanelInfo(){
        Map result = new HashMap<>();

        try{
            result.put("data",userService.getUserHome());
            result.put("responseCode", Code.COMMON_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();

            result.put("responseCode", Code.COMMON_FAIL);
            result.put("responseMsg", "获取用户主页信息失败");
        }

        return result;
    }

    @RequestMapping("/userInfo")
    public Map getUserSettingInfo(){
        Map result = new HashMap<>();

        try{
            result.put("data",userService.getUserSetting());
            result.put("responseCode", Code.COMMON_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();

            result.put("responseCode", Code.COMMON_FAIL);
            result.put("responseMsg", "获取用户信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public Map signUp(@RequestParam String username, @RequestParam String nickname,
                      @RequestParam String email, @RequestParam String password){
        Map result = new HashMap<>();
        //判断用户名是否被占用
        if(userService.findByUsername(username) != null){
            result.put("responseCode", Code.COMMON_FAIL);
            result.put("responseMsg", "该用户名已存在");
        }else {
            User user = new User(username, nickname, email, password);

            userService.persist(user);

            httpSession.setAttribute("uid",user.getId());
            httpSession.setAttribute("photo",user.getPhoto());
            httpSession.setAttribute("nickname",nickname);
            httpSession.setAttribute("email",email);

            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("responseMsg", "注册成功");
        }

        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map updateUserInfo(@RequestParam String email, @RequestParam String nickname){
        Map result = new HashMap<>();

        if((Long)httpSession.getAttribute("uid") == -1L){
            User user = new User((String)httpSession.getAttribute("photo"),
                                 (String)httpSession.getAttribute("group"),
                                 (String)httpSession.getAttribute("groupid"),
                                 (String)httpSession.getAttribute("groupnickname"), email, nickname);
            userService.persist(user);

            httpSession.setAttribute("uid",user.getId());

            httpSession.setAttribute("nickname",nickname);
            httpSession.setAttribute("email",email);

            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("responseMsg", "第三方登陆初始化成功");
        }else{
            Long uid =(Long)httpSession.getAttribute("uid");
            User user = userService.find(uid);

            user.setEmail(email);
            user.setNickname(nickname);

            httpSession.setAttribute("nickname",nickname);
            httpSession.setAttribute("email",email);

            userService.merge(user);

            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("responseMsg", "用户信息更新成功");
        }

        return result;
    }
}
