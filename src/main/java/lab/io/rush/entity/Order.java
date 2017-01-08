package lab.io.rush.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyang on 16/12/30.
 */

@Entity
@Table (name = "order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long movieid;
    private Long userid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    public Order() {}

    public Order(Long movieid, Long userid) {
        this.movieid = movieid;
        this.userid = userid;
    }

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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", movieid=" + movieid +
                ", userid=" + userid +
                ", createdat=" + createtime +
                '}';
    }
}
