package lab.io.rush.dao;

import lab.io.rush.entity.Movie;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
public interface MovieDao  {

    @Cacheable(value = "Movie", key = "'MovieWithTag-id:'+#movieid")
    Movie find(Long movieid);

    @Cacheable(value = "Movie", key = "'MovieNoTag-id:'+#movieid")
    Movie findNoTag(Long movieid);

    List<Movie> findAll();

    List<Movie> findAllNoTag();

    @Cacheable(value = "Stock", key = "'Stock-id:'+#movieid")
    int getStock(Long movieid);

    void persist(Movie movie);

    @Caching(evict = {
            @CacheEvict(value = "Movie",  key = "'MovieWithTag-id:'+#movie.id", beforeInvocation = true),
            @CacheEvict(value = "Movie", key = "'MovieNoTag-id:'+#movie.id", beforeInvocation = true),
            @CacheEvict(value = "Stock", key = "'Movie-id:'+#movie.id", beforeInvocation = true)
    })
    Movie merge(Movie movie);
}
