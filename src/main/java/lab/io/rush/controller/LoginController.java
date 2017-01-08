package lab.io.rush.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.io.rush.dao.UserDao;
import lab.io.rush.entity.User;
import lab.io.rush.service.LoginService;
import lab.io.rush.dto.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by liyang on 16/12/30.
 */
@RestController
public class LoginController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginService loginService;
    @Autowired
    private HttpSession httpSession;

    private static final String wxAppId = "your wxid";
    private static final String wbAppId = "your wbid";

    @RequestMapping("/checkLogin")
    public Map checkLogin() throws IOException{
        Map result = new HashMap<>();

        Object uid = httpSession.getAttribute("uid");

        if(uid != null){
            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("responseMsg", "确认已登陆");
        }else{
            result.put("responseCode", Code.NOT_LOGIN);
            result.put("responseMsg", "检测登录态失败");
        }

        return result;
    }

    @RequestMapping("/getLoginTicket")
    public Map getTicket(@RequestParam(value="username", defaultValue = "") String username) {
        Map result = new HashMap();

        User user = userDao.findByUsername(username);

        if(user == null){
            result.put("responseCode", Code.COMMON_FAIL);
            result.put("responseMsg", "没有对应的用户名");
        }
        else {
            user.setTimestamp(System.currentTimeMillis()/1000);

            try {
                userDao.merge(user);

                Map data = new HashMap();
                data.put("timestamp", user.getTimestamp());
                result.put("responseCode", Code.COMMON_SUCCESS);
                result.put("data", data);
            }
            catch (Exception e) {
                e.printStackTrace();
                result.put("responseCode", Code.DB_EXCEPTION);
                result.put("responseMsg", "数据库操作错误");
            }
        }

        return result;
    }

    @RequestMapping("/userLogin")
    @ResponseBody
    public Map userLogin(@RequestParam(value="username", defaultValue = "") String username,
                         @RequestParam(value="password", defaultValue = "") String password) {
        Map result = new HashMap();

        User user = userDao.findByUsername(username);


        if(user == null){
            result.put("responseCode", Code.COMMON_FAIL);
            result.put("responseMsg", "没有对应的用户名");
        }
        else {
            if(user.getTimestamp() == 0) {

                System.out.println("\n\n"+user.toString());
                System.out.println("\n\nuser timestamp "+user.getTimestamp());

                result.put("user",user);

                result.put("responseCode", Code.DATA_NOT_FOUND);
                result.put("responseMsg", "登陆错误");
            }  // No timestamp, return error
            else {
                String originStr = user.getPassword() + user.getTimestamp();
                String hashedPswStr = DigestUtils.md5DigestAsHex(originStr.getBytes());

                if (hashedPswStr.equals(password)) {
                    user.setTimestamp((long) 0);
                    try {
                        userDao.merge(user);

//                        httpSession.removeAttribute("uid");
//                        httpSession.removeAttribute("photo");
//                        httpSession.removeAttribute("nickname");
//                        httpSession.removeAttribute("email");

                        httpSession.setAttribute("uid", user.getId());
                        httpSession.setAttribute("photo",user.getPhoto());
                        httpSession.setAttribute("nickname",user.getNickname());
                        httpSession.setAttribute("email",user.getEmail());


                        result.put("httpSessionUid",httpSession.getAttribute("uid"));


                        result.put("responseCode", Code.COMMON_SUCCESS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.put("responseCode", Code.DB_EXCEPTION);
                        result.put("responseMsg", "没有对应的用户名");
                    }
                } else {
                    result.put("responseCode", Code.COMMON_FAIL);
                    result.put("responseMsg", "密码错误");
                }
            }  // Auth with timestamp
        }

        return result;
    }

    @RequestMapping("/encryptForTest")
    public String encrypt(@RequestParam(value="password", defaultValue = "testpassword") String password,
                          @RequestParam(value="salt", defaultValue = "1483861299") String salt) {
        String originStr =  DigestUtils.md5DigestAsHex(password.getBytes()) + salt;

        return DigestUtils.md5DigestAsHex(originStr.getBytes());
    }

    @RequestMapping("/getWxAppid")
    public Map getWxAppid(){
        Map result = new HashMap<>();

        result.put("appid", wxAppId);

        return result;
    }

    @RequestMapping("/getWbAppid")
    public Map getWbAppid(){
        Map result = new HashMap<>();

        result.put("appid",wbAppId);

        return result;
    }

    @RequestMapping("/wxLogin")
    public Map wechatLogin(@RequestParam(value="code", defaultValue = "") String code) {
        Map baseInfo = loginService.wxUserBaseInfo(code);
        Map detailInfo = loginService.wxUserDetailInfo(baseInfo); // 用户详情

        httpSession.setAttribute("groupid", detailInfo.get("openid"));
        httpSession.setAttribute("group","wx");
        httpSession.setAttribute("groupnickname",detailInfo.get("nickname"));
        httpSession.setAttribute("photo",detailInfo.get("headimgurl"));

        return loginService.loginUserProcess((String)detailInfo.get("openid"),"wx");
    }

    @RequestMapping("/wbLogin")
    public Map weiboLogin(@RequestParam(value="code", defaultValue = "") String code) throws IOException {
        Map baseInfo = loginService.wbUserBaseInfo(code);
        Map detailInfo = loginService.wbUserDetailInfo(baseInfo); // 用户详情

        httpSession.setAttribute("groupid", detailInfo.get("idstr"));
        httpSession.setAttribute("group","wb");
        httpSession.setAttribute("groupnickname",detailInfo.get("screen_name"));
        httpSession.setAttribute("photo",detailInfo.get("avatar_large"));

        return loginService.loginUserProcess((String)detailInfo.get("uid"),"wb");
    }

    @RequestMapping("/logout")
    public void logout(@RequestParam(value = "redirect_url") String redirect_url, HttpServletResponse response) throws IOException {
        httpSession.removeAttribute("uid");

        response.sendRedirect(URLDecoder.decode(redirect_url,"UTF-8"));
    }
}