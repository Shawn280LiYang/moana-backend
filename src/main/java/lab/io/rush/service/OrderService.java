package lab.io.rush.service;

import lab.io.rush.dto.OrderDto;
import lab.io.rush.entity.Order;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by liyang on 17/1/7.
 */
public interface OrderService {

    @Cacheable(value = "OrderDto", key = "'OrderDto-orderid'+#orderid")
    OrderDto getOrderById(Long orderid);

    List<OrderDto> findByMovieidAndUserid(Long movieid, Long userid);

    void persist(Order order);
}
