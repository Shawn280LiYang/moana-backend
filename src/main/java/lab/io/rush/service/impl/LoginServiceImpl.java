package lab.io.rush.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.io.rush.dao.UserDao;
import lab.io.rush.entity.User;
import lab.io.rush.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyang on 17/1/7.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpSession httpSession;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private static final String wechatAppId = "your wx appid";
    private static final String wechatAppSecret = "your wx appSecret";

    private static final String weiboAppId = "your wb appid";
    private static final String weiboAppSecret = "your wb appSecret";

    @Override
    public void loginUserProcess(String groupid, String group){

        User user = userDao.findByGroupidAndGroup(groupid,group);

        if(user != null){ // 老用户
            httpSession.setAttribute("uid",user.getId());
            httpSession.setAttribute("email",user.getEmail());
            httpSession.setAttribute("nickname",user.getNickname());

            user.setPhoto((String)httpSession.getAttribute("photo"));
            user.setGroupnickname((String)httpSession.getAttribute("groupnickname"));

            userDao.merge(user);
        } else{ //新用户

            httpSession.setAttribute("uid", -1L);
        }
    }


    @Override
    public Map wxUserBaseInfo(String code) {
        String _url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                wechatAppId, wechatAppSecret, code);

        return restTemplateGet(_url);
    }

    @Override
    public Map wxUserDetailInfo(Map baseInfo) {
        String _url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN",
                baseInfo.get("access_token"), baseInfo.get("openid"));

        return restTemplateGet(_url);
    }

    @Override
    public Map wbUserBaseInfo(String code) {
        Map res = new HashMap();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("client_id", weiboAppId);
        map.add("client_secret", weiboAppSecret);
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", "http://dev5.sprintechco.com/java/moana/wbLogin?backUrl=dev5.sprintechco.com");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String _url = String.format("https://api.weibo.com/oauth2/access_token");

        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(_url, request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            res = objectMapper.readValue(result, HashMap.class);
            logger.info(res.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Map wbUserDetailInfo(Map baseInfo) {
        String _url = String.format("https://api.weibo.com/2/users/show.json?uid=%s&access_token=%s",
                baseInfo.get("uid"), baseInfo.get("access_token"));

        return restTemplateGet(_url);
    }

    @Override
    public Map restTemplateGet(String _url) {
        Map res = new HashMap();

        try{
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(_url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            res = objectMapper.readValue(result, HashMap.class);
            logger.info(res.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }
}
