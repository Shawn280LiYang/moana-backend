package lab.io.rush.service;

import lab.io.rush.dto.MovieDto;
import lab.io.rush.entity.Movie;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * Created by liyang on 17/1/4.
 */
public interface MovieService {

    MovieDto getMovieById(Long id);

    @Cacheable(value = "Movie", key = "'MovieNoTag-id:'+#movieid")
    Movie getMovieNoTag(Long movieid);

    List<MovieDto> getMovieAll();
    List<Movie> getMovieALlNoTag();

    @Cacheable(value = "Stock", key = "'Stock-id:'+#movieid")
    int getStock(Long movieid);

    @Caching(evict = {
            @CacheEvict(value = "Movie",  key = "'MovieWithTag-id:'+#movie.id", beforeInvocation = true),
            @CacheEvict(value = "Movie", key = "'MovieNoTag-id:'+#movie.id", beforeInvocation = true),
            @CacheEvict(value = "Stock", key = "'Movie-id:'+#movie.id", beforeInvocation = true)
    })
    Movie merge(Movie movie);
}
