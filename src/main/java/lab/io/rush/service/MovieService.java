package lab.io.rush.service;

import lab.io.rush.dto.MovieDto;
import lab.io.rush.entity.Movie;

import java.util.List;

/**
 * Created by liyang on 17/1/4.
 */
public interface MovieService {

    MovieDto getMovieById(Long id);

    Movie getMovieNoTag(Long movieid);

    List<MovieDto> getMovieAll();
    List<Movie> getMovieALlNoTag();

    int getStock(Long movieid);

    Movie merge(Movie movie);
}
