package lab.io.rush.entity;

import lab.io.rush.entity.Movie;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by liyang on 17/1/1.
 */
@Entity
@Table (name="tag")
public class Tag implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private Set<Movie> movies;

    public Tag() {
    }

    public Tag(String name, Set<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "tags")
    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
