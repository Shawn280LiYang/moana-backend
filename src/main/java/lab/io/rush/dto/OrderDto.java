package lab.io.rush.dto;

import java.io.Serializable;

/**
 * Created by liyang on 17/1/4.
 */
public class OrderDto implements Serializable {

    private Long id;
    private String moviename;
    private String movieimgurl;
    private String createtime;
    private String movieshowtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMovieimgurl() {
        return movieimgurl;
    }

    public void setMovieimgurl(String movieimgurl) {
        this.movieimgurl = movieimgurl;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMovieshowtime() {
        return movieshowtime;
    }

    public void setMovieshowtime(String movieshowtime) {
        this.movieshowtime = movieshowtime;
    }
}
