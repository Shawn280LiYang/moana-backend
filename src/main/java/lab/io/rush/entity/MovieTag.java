package lab.io.rush.entity;

import javax.persistence.*;

/**
 * Created by liyang on 17/1/11.
 */
@Entity
@Table(name = "MOVIE_TAG")
public class MovieTag {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long movieid;

    private Long tagid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieid() {
        return movieid;
    }

    public void setMovieid(Long movieid) {
        this.movieid = movieid;
    }

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }
}
