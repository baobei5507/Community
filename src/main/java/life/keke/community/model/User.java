package life.keke.community.model;

import org.apache.ibatis.annotations.Insert;

public class User {
    private String name;
    private String account_id;
    private String token;
    private  long gmtCreate;
    private long gmtModified;

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
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmt_modified) {
        this.gmtModified = gmtModified;
    }
}
