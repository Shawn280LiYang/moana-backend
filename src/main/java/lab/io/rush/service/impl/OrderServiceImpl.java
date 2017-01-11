package lab.io.rush.service.impl;

import lab.io.rush.dao.OrderDao;
import lab.io.rush.dto.OrderDto;
import lab.io.rush.entity.Movie;
import lab.io.rush.entity.Order;
import lab.io.rush.service.MovieService;
import lab.io.rush.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyang on 17/1/7.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MovieService movieService;

    private SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");

    @Override
    public OrderDto getOrderDto(Order entity) {
        OrderDto dto = null;

        if(entity != null){
            dto = new OrderDto();

            Movie movie =movieService.findNoTag(entity.getMovieid());

            dto.setId(entity.getId());
            dto.setMovieimgurl(movie.getImgurl());
            dto.setMoviename(movie.getName());
            dto.setMovieshowtime(df.format(movie.getShowtime()));
            dto.setCreatetime(df.format(entity.getCreatetime()));
        }

        return dto;
    }

    @Override
    public List<OrderDto> findByMovieidAndUserid(Long movieid, Long userid) {
        List<OrderDto> dtoList = null;

        List<Order> entityList = orderDao.findByMovieidAndUserid(movieid, userid);

        if(entityList!=null && entityList.size() > 0){
            dtoList = new ArrayList<>();

            for(int i = 0;i < entityList.size(); i ++){
                dtoList.add(getOrderDto(entityList.get(i)));
            }
        }
        return dtoList;
    }

    @Override
    public void persist(Order order) {
        orderDao.persist(order);
    }
}
