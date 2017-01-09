package lab.io.rush.dao;

import lab.io.rush.entity.Order;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
public interface OrderDao {

    @Cacheable(value = "OrderDto", key = "'OrderDto-orderid'+#orderid")
    Order find(Long orderid);

    List<Order> findByUserid(Long userid);

    List<Order> findByMovieidAndUserid(Long movieid, Long userid);

    void persist(Order order);
}
