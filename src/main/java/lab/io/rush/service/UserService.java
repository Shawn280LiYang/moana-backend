package lab.io.rush.service;

import lab.io.rush.dto.UserPanelDto;
import lab.io.rush.dto.UserInfoDto;

/**
 * Created by liyang on 17/1/4.
 */
public interface UserService {
    UserPanelDto getUserHome();
    UserInfoDto getUserSetting();
}
