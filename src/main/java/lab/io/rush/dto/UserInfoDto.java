package lab.io.rush.dto;

/**
 * Created by liyang on 17/1/4.
 */
public class UserInfoDto {
    private String photo;
    private String nickname;
    private String email;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
