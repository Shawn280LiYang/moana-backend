package lab.io.rush.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by liyang on 16/12/30.
 */
@Entity
@Table(name = "MOVIE")
public class Movie implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer ticketstock;
    @Temporal(TemporalType.TIMESTAMP)
    private Date showtime;
    @Column(nullable = false, length=24)//可显示12个字简介
    private String briefintro;
    private String imgurl;

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

    public Integer getTicketstock() {
        return ticketstock;
    }

    public void setTicketstock(Integer ticketstock) {
        this.ticketstock = ticketstock;
    }

    public Date getShowtime() {
        return showtime;
    }

    public void setShowtime(Date showtime) {
        this.showtime = showtime;
    }

    public String getBriefintro() {
        return briefintro;
    }

    public void setBriefintro(String briefintro) {
        this.briefintro = briefintro;
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_tag",
            joinColumns = {@JoinColumn(name = "movieid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tagid", referencedColumnName ="id")})

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticketstock=" + ticketstock +
                ", showtime=" + showtime +
                ", briefintro='" + briefintro + '\'' +
                '}';
    }
}
