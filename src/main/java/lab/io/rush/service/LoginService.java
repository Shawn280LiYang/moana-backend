package lab.io.rush.service;

import java.util.Map;

/**
 * Created by liyang on 17/1/7.
 */
public interface LoginService {
    void loginUserProcess(String uid, String group);

    Map wxUserBaseInfo(String code);
    Map wxUserDetailInfo(Map baseInfo);

    Map wbUserBaseInfo(String code);
    Map wbUserDetailInfo(Map baseInfo);

    Map restTemplateGet(String _url);
}
