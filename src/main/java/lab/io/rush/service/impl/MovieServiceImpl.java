package lab.io.rush.service.impl;

import lab.io.rush.dao.MovieDao;
import lab.io.rush.dao.MovieTagDao;
import lab.io.rush.dao.TagDao;
import lab.io.rush.dto.MovieDto;
import lab.io.rush.entity.Movie;
import lab.io.rush.entity.MovieTag;
import lab.io.rush.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyang on 17/1/4.
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private MovieTagDao movieTagDao;
    @Autowired
    private TagDao tagDao;

    private SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");

    @Override
    public Movie findNoTag(Long id) {
        return movieDao.findNoTag(id);
    }

    @Override
    public List<Movie> getMovieALlNoTag() {
        return movieDao.findAllNoTag();
    }

    @Override
    public MovieDto getMovieDto(Long id) {
        MovieDto dto = null;
        Movie entity =  movieDao.findNoTag(id);

        if(entity != null){
            dto = new MovieDto();

            dto.setId(id);
            dto.setName(entity.getName());
            dto.setTicketstock(entity.getTicketstock());
            dto.setBriefintro(entity.getBriefintro());
            dto.setImgurl(entity.getImgurl());
            dto.setShowtime(df.format(entity.getShowtime()));

            List<MovieTag> movieTagList = movieTagDao.findMovieTagByMovieid(id);

            if(movieTagList!=null) {

                List<String> tags = new ArrayList<>();

                for(int i=0; i<movieTagList.size();i++){
                    Long tagid = movieTagList.get(i).getTagid();
                    tags.add(tagDao.find(tagid).getName());
                }

                dto.setTags(tags);
            }
        }

        return dto;
    }

    @Override
    public List<MovieDto> getMovieAllWithTag() {
        List<MovieDto> dtoList = null;

        List<Movie> movieList = movieDao.findAllNoTag();

        if(movieList!=null && movieList.size()>0){

            dtoList = new ArrayList<>();

            for(int i=0; i<movieList.size(); i++){

                MovieDto dto = getMovieDto(movieList.get(i).getId());

                dtoList.add(dto);
            }
        }

        return dtoList;
    }

    @Override
    public int getStock(Long movieid) {
        return movieDao.getStock(movieid);
    }

    @Override
    public Movie merge(Movie movie) {
        return movieDao.merge(movie);
    }
}
