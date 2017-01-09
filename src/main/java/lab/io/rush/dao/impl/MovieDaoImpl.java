package lab.io.rush.dao.impl;

import lab.io.rush.dao.MovieDao;
import lab.io.rush.dao.datanucleus.DataNucleusDao;
import lab.io.rush.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
@Repository
public class MovieDaoImpl implements MovieDao {

    @Autowired
    private DataNucleusDao dataNucleusDao;

    private static final Logger logger = LoggerFactory.getLogger(MovieDao.class);

    @Override
    public Movie find(Long movieid) {
        return dataNucleusDao.find(Movie.class, movieid);
    }

    @Override
    public List<Movie> findAllNoTag() {
        return dataNucleusDao.querySelect("SELECT m FROM Movie m ORDER BY m.id ASC");
    }

    @Override
    public int getStock(Long movieid) {
        Movie movie = find(movieid);

        if(movie == null){
            return -1;
        }else{
            return movie.getTicketstock();
        }
    }

    @Override
    public void persist(Movie movie) {
        dataNucleusDao.persist(movie);
    }

    @Override
    public Movie merge(Movie movie) {
        return dataNucleusDao.merge(movie);
    }
}
