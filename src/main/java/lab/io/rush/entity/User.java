package lab.io.rush.entity;

/**
 * Created by liyang on 14/10/2016.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(unique = true, length = 48)
    private String username;
    @JsonIgnore
    private String password;
    @Column(nullable = false, length = 48)
    private String email;

    private String nickname;

    private String photo;

    private Long timestamp;

    private String usergroup;

    private String groupid;

    private String groupnickname;

    public User() {
    }

    //for signUp
    public User(String username, String nickname, String email, String password) {
        this.usergroup = "user"; //用户注册默认分组'user'
        this.photo = "img/myheader.png"; // 用户注册获得默认头像
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    //for first sign wx/wb login
    public User(String photo, String usergroup, String groupid, String groupnickname, String email, String nickname) {
        this.photo = photo;
        this.usergroup = usergroup;
        this.groupid = groupid;
        this.groupnickname = groupnickname;
        this.email = email;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupnickname() {
        return groupnickname;
    }

    public void setGroupnickname(String groupnickname) {
        this.groupnickname = groupnickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", photo='" + photo + '\'' +
                ", timestamp=" + timestamp +
                ", usergroup='" + usergroup + '\'' +
                ", groupid='" + groupid + '\'' +
                ", groupnickname='" + groupnickname + '\'' +
                '}';
    }
}
