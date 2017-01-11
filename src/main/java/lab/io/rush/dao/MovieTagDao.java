package lab.io.rush.dao;

import lab.io.rush.entity.MovieTag;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
public interface MovieTagDao  {

    List<MovieTag> findMovieTagByMovieid(Long movieid);

}
