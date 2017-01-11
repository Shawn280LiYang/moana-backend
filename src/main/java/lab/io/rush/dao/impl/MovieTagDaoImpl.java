package lab.io.rush.dao.impl;

import lab.io.rush.dao.MovieTagDao;
import lab.io.rush.dao.datanucleus.DataNucleusDao;
import lab.io.rush.entity.MovieTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
@Repository
public class MovieTagDaoImpl implements MovieTagDao {

    @Autowired
    private DataNucleusDao dataNucleusDao;

    @Override
    public List<MovieTag> findMovieTagByMovieid(Long movieid) {
        return dataNucleusDao.querySelect("SELECT m FROM MovieTag m WHERE m.movieid = " + movieid);
    }
}
