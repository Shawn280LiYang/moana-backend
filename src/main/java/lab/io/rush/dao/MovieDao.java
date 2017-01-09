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

    @Cacheable(value = "Movie", key = "'Movie-id:'+#movieid")
    Movie find(Long movieid);

    List<Movie> findAllNoTag();

    @Cacheable(value = "Stock", key = "'Stock-id:'+#movieid")
    int getStock(Long movieid);

    void persist(Movie movie);

    @Caching(evict = {
            @CacheEvict(value = "Movie",  key = "'Movie-id:'+#movie.id", beforeInvocation = true),
            @CacheEvict(value = "Stock", key = "'Stock-id:'+#movie.id", beforeInvocation = true)
    })
    Movie merge(Movie movie);
}
