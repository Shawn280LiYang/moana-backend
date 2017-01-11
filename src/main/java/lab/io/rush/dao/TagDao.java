package lab.io.rush.dao;

import lab.io.rush.entity.Tag;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by liyang on 17/1/2.
 */
public interface TagDao {

    @Cacheable(value = "Tag", key = "'Tag-id:'+#id")
    Tag find(Long id);

}
