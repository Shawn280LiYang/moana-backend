package lab.io.rush.service;

import lab.io.rush.dto.OrderDto;
import lab.io.rush.entity.Order;

import java.util.List;

/**
 * Created by liyang on 17/1/7.
 */
public interface OrderService {

    OrderDto getOrderDto(Order order);

    List<OrderDto> findByMovieidAndUserid(Long movieid, Long userid);

    void persist(Order order);
}
