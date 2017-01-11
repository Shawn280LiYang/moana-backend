package lab.io.rush.dao.impl;

import lab.io.rush.dao.OrderDao;
import lab.io.rush.dao.datanucleus.DataNucleusDao;
import lab.io.rush.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    @Qualifier("dataNucleusDaoImpl") // TODO 观察可不可以去掉
    private DataNucleusDao dataNucleusDao;

    @Override
    public Order find(Long orderid) {
        return dataNucleusDao.find(Order.class, orderid);
    }

    @Override
    public List<Order> findByUserid(Long userid) {
        return dataNucleusDao.querySelect("SELECT o FROM Order o WHERE o.userid = "+userid+" ORDER BY o.createtime ASC");
    }

    @Override
    public List<Order> findByMovieidAndUserid(Long movieid, Long userid) {
        return dataNucleusDao.querySelect("SELECT o FROM Order o WHERE o.movieid = "+movieid+" AND o.userid = "+userid);
    }

    @Override
    public void persist(Order order) {
        dataNucleusDao.persist(order);
    }
}
