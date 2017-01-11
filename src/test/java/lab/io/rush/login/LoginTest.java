package lab.io.rush.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by liyang on 24/12/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @Test
    public void getTicket() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "testuser");
        String result = restTemplate.postForObject("/getLoginTicket", form, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map res = new HashMap();
        try {
            res = objectMapper.readValue(result, HashMap.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(res.toString());
        assertThat(res.get("responseCode")).isEqualTo(0);
        assertThat(res.get("data")).isNotNull();
    }

    @Test
    public void userLogin() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "testuser");
        String originStr = new String();
        String result = this.restTemplate.postForObject("/getLoginTicket", form, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map res = new HashMap();
        try {
            res = objectMapper.readValue(result, HashMap.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            originStr = DigestUtils.md5DigestAsHex("testpassword".getBytes()) + ((Map)res.get("data")).get("timestamp");
            MultiValueMap<String, String> loginRequest = new LinkedMultiValueMap<String, String>();
            loginRequest.add("username", "testuser");
            loginRequest.add("password", DigestUtils.md5DigestAsHex(originStr.getBytes()));
            String loginResult = this.restTemplate.postForObject("/userLogin", loginRequest, String.class);
            res = objectMapper.readValue(loginResult, HashMap.class);
            logger.info(res.toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        assertThat(res.get("responseCode")).isEqualTo(0);
    }
}
