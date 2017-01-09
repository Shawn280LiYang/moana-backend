package lab.io.rush.dao;

import lab.io.rush.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

/**
 * Created by liyang on 17/1/2.
 */
public interface UserDao {
    void persist(User user);

    @Caching(evict = {
            @CacheEvict(value="User", key = "'User-id:'+#user.id", beforeInvocation = true),
            @CacheEvict(value="User", key = "'User-username'+#user.username", beforeInvocation = true),
            @CacheEvict(value="User", key = "'User-group'+#user.usergroup+'groupid'+#user.groupid", beforeInvocation = true)
    })
    User merge(User user);

    @Cacheable(value = "User", key = "'User-id'+#id")
    User find(Long id);

    @Cacheable(value = "User", key = "'User-username'+#username")
    User findByUsername(String username);

    @Cacheable(value = "User", key = "'User-group'+#group+'groupid'+#groupid")
    User findByGroupidAndGroup(String groupid, String group);

}
