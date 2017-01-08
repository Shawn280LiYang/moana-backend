package lab.io.rush.dto;

import java.util.List;

/**
 * Created by liyang on 17/1/4.
 */
public class MovieDto {
    private Long id;
    private String name;
    private Integer ticketstock;
    private String showtime;
    private String briefintro;
    private String imgurl;
    private List<String> tags;

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

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getBriefintro() {
        return briefintro;
    }

    public void setBriefintro(String briefintro) {
        this.briefintro = briefintro;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
