package lab.io.rush.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by liyang on 17/1/1.
 */
@Entity
@Table (name="TAG")
public class Tag implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;


    public Tag() {
    }

    public Tag(String name, Set<Movie> movies) {
        this.name = name;
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
