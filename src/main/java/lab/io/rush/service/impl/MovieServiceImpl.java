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
    public MovieDto getMovieById(Long id) {
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

            List<String> tags = null;
            if(entity.getTags()!=null) {
                tags = new ArrayList<>();
                tags.addAll(entity.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
                dto.setTags(tags);
            }
        }

        return dto;
    }

    @Override
    public Movie getMovieNoTag(Long movieid) {
        return movieDao.findNoTag(movieid);
    }

    @Override
    public List<MovieDto> getMovieAll() {
        List<MovieDto> dtoList = null;

        List<Movie> entityList = movieDao.findAll();

        if(entityList!=null && entityList.size() > 0){
            dtoList = new ArrayList<>();

            for(int i = 0;i < entityList.size(); i ++){
                dtoList.add(getMovieById(entityList.get(i).getId()));
            }
        }
        return dtoList;
    }

    @Override
    public List<Movie> getMovieALlNoTag() {
        return movieDao.findAllNoTag();
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
