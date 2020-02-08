package life.keke.community.dto;

public class GithubUser {
    private String name;
    private long id;
    private String bio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name+"如果此处显示这段文字，那么名字就为Null";
    }

    public void setName(String name) {
        this.name = name;
    }
}
