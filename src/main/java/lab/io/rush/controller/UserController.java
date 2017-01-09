package lab.io.rush.controller;

import lab.io.rush.dao.UserDao;
import lab.io.rush.dto.Code;
import lab.io.rush.dto.UserPanelDto;
import lab.io.rush.dto.UserInfoDto;
import lab.io.rush.entity.User;
import lab.io.rush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
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
    public UserPanelDto getUserPanelInfo(){
        return userService.getUserHome();
    }

    @RequestMapping("/userInfo")
    public UserInfoDto getUserSettingInfo(){
        return userService.getUserSetting();
    }

    @RequestMapping("/testSetSession")
    public String testSetSession(){
        this.httpSession.setAttribute("test", "This is a test");
        return new String("finished");
    }

    @RequestMapping("/testGetSession")
    public String testGetSession(){
        return (String) this.httpSession.getAttribute("test");
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
            // 原密码进行一次md5加密
            User user = new User("login", username, nickname, email, DigestUtils.md5DigestAsHex(password.getBytes()));
            //存入数据库
            userService.persist(user);
            // 给与登录态并跳转(前端负责跳转)
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

        if(httpSession.getAttribute("uid") == null){
            User user = new User((String)httpSession.getAttribute("photo"),
                                 (String)httpSession.getAttribute("group"),
                                 (String)httpSession.getAttribute("groupid"),
                                 (String)httpSession.getAttribute("groupnickname"), email, nickname);
            //保存数据库
            userService.persist(user);
            //标记登录态
            httpSession.setAttribute("uid",user.getId());

            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("responseMsg", "第三方登陆初始化成功");
        }else{
            User user = userService.find((Long)httpSession.getAttribute("uid"));
            //更新email和nickname
            user.setEmail(email);
            user.setNickname(nickname);

            //更新数据库
            userService.merge(user);

            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("responseMsg", "用户信息更新成功");
        }

        return result;
    }
}
