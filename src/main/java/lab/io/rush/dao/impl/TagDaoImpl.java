package lab.io.rush.dao.impl;

import lab.io.rush.dao.TagDao;
import lab.io.rush.entity.Tag;
import lab.io.rush.dao.datanucleus.DataNucleusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by liyang on 17/1/2.
 */
@Repository
public class TagDaoImpl implements TagDao {
    @Autowired
    @Qualifier("dataNucleusDaoImpl")
    DataNucleusDao dataNucleusDao;

    @Override
    public void persist(Tag tag) {
        dataNucleusDao.persist(tag);
    }
}
