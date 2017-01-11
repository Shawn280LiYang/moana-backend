package lab.io.rush.service.impl;

import lab.io.rush.dao.OrderDao;
import lab.io.rush.dao.UserDao;
import lab.io.rush.dto.OrderDto;
import lab.io.rush.dto.UserPanelDto;
import lab.io.rush.dto.UserInfoDto;
import lab.io.rush.entity.Order;
import lab.io.rush.entity.User;
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
    private UserDao userDao;
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

        System.out.println("\n\nhttpSession uid is: "+httpSession.getAttribute("uid")+"\n");

        List<Order> orderList = orderDao.findByUserid((Long)httpSession.getAttribute("uid"));

        System.out.println("查到的orderList为: "+orderList);

        if(orderList!=null){
            orderDtoList = new ArrayList<>();
            for(int i=0;i<orderList.size();i++){
                orderDtoList.add(orderService.getOrderDto(orderList.get(i)));
            }
        }
        dto.setOrderDtoList(orderDtoList);

        return dto;
    }

    @Override
    public UserInfoDto getUserSetting() {
        UserInfoDto dto = new UserInfoDto();

        // nickname 新老用户做判断
        if((Long)httpSession.getAttribute("uid") == -1L){
            dto.setNickname((String)httpSession.getAttribute("groupnickname"));
        }else{
            dto.setNickname((String)httpSession.getAttribute("nickname"));
        }

        dto.setPhoto((String)httpSession.getAttribute("photo"));
        dto.setEmail((String)httpSession.getAttribute("email"));

        return dto;
    }

    @Override
    public User find(Long id) {
        return userDao.find(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void persist(User user) {
        userDao.persist(user);
    }

    @Override
    public User merge(User user) {
        return userDao.merge(user);
    }

}
