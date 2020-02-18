package life.keke.community.model;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class User implements Serializable {

    private  Integer id;
    private String name;
    private String account_id;
    private String bio;
    private String token;
    private  long gmtCreate;
    private long gmtModified;
    private String avatarUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmt_create) {
        this.gmtCreate = gmt_create;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmt_modified) {
        this.gmtModified = gmt_modified;
    }
}
