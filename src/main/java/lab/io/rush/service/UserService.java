package lab.io.rush.service;

import lab.io.rush.dto.UserPanelDto;
import lab.io.rush.dto.UserInfoDto;
import lab.io.rush.entity.User;

/**
 * Created by liyang on 17/1/4.
 */
public interface UserService {

    UserPanelDto getUserHome();

    UserInfoDto getUserSetting();

    User find(Long id);

    User findByUsername(String username);

    void persist(User user);

    User merge(User user);

}
