package lab.io.rush.service.impl;

import lab.io.rush.dao.OrderDao;
import lab.io.rush.dto.OrderDto;
import lab.io.rush.dto.UserPanelDto;
import lab.io.rush.dto.UserInfoDto;
import lab.io.rush.entity.Order;
import lab.io.rush.service.OrderService;
import lab.io.rush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyang on 17/1/4.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpSession httpSession;

    @Override
    public UserPanelDto getUserHome(){
        UserPanelDto dto = new UserPanelDto();

        dto.setId((Long)httpSession.getAttribute("uid"));
        dto.setPhoto((String)httpSession.getAttribute("photo"));
        dto.setNickname((String)httpSession.getAttribute("nickname"));

        List<OrderDto> orderDtoList = null;
        List<Order> orderList = orderDao.findByUserid((Long)httpSession.getAttribute("uid"));

        if(orderDtoList!=null){
            orderDtoList = new ArrayList<>();
            for(int i=0;i<orderList.size();i++){
                orderDtoList.add(orderService.getOrderById(orderList.get(i).getId()));
            }
        }
        dto.setOrderDtoList(orderDtoList);

        return dto;
    }

    @Override
    public UserInfoDto getUserSetting() {
        UserInfoDto dto = new UserInfoDto();

        dto.setNickname((String)httpSession.getAttribute("nickname"));
        dto.setPhoto((String)httpSession.getAttribute("photo"));
        dto.setEmail((String)httpSession.getAttribute("email"));

        return dto;
    }
}
