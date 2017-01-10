package lab.io.rush.dao.impl;

import lab.io.rush.dao.UserDao;
import lab.io.rush.dao.datanucleus.DataNucleusDao;
import lab.io.rush.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    @Qualifier("dataNucleusDaoImpl")
    private DataNucleusDao dataNucleusDao;

    @Override
    public User find(Long id) {
        return dataNucleusDao.find(User.class, id);
    }

    @Override
    public void persist(User user) {
        dataNucleusDao.persist(user);
    }

    @Override
    public User merge(User user) {
        return dataNucleusDao.merge(user);
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = dataNucleusDao.querySelect("SELECT u FROM User u WHERE u.username = '"+username+"'");
        if((users == null) || (users!=null && users.size()!=1)) return null;
        else return users.get(0);
    }

    @Override
    public User findByGroupidAndGroup(String groupid, String group) {
        List<User> users = dataNucleusDao.querySelect("SELECT u FROM User u WHERE u.groupid = '"+groupid+"' AND u.usergroup = '"+group+"'");
        if((users == null) || (users!=null && users.size()!=1)) return null;
        else return users.get(0);
    }
}
