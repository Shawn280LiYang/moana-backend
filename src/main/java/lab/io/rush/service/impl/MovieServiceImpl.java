package lab.io.rush.service.impl;

import lab.io.rush.dao.MovieDao;
import lab.io.rush.dto.MovieDto;
import lab.io.rush.entity.Movie;
import lab.io.rush.entity.Tag;
import lab.io.rush.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liyang on 17/1/4.
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    private SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");

    @Override
    public Movie find(Long id) {
        return movieDao.find(id);
    }

    @Override
    public MovieDto getMovieDto(Long id) {
        MovieDto dto = null;
        Movie entity =  movieDao.find(id);

        if(entity != null){
            dto = new MovieDto();

            dto.setId(id);
            dto.setName(entity.getName());
            dto.setTicketstock(entity.getTicketstock());
            dto.setBriefintro(entity.getBriefintro());
            dto.setImgurl(entity.getImgurl());
            dto.setShowtime(df.format(entity.getShowtime()));

            if(entity.getTags()!=null) {
                List<String> tags = new ArrayList<>();

                tags.addAll(entity.getTags().stream().map(Tag::getName).collect(Collectors.toList()));

                dto.setTags(tags);
            }
        }

        return dto;
    }

    @Override
    public List<Movie> getMovieALlNoTag() {
        return movieDao.findAllNoTag();
    }

    @Override
    public List<MovieDto> getMovieAll() {
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
