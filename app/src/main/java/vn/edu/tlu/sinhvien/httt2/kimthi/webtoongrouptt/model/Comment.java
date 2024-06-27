package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class Comment {
    private int id;
    private String content;
    private User user;
    private Integer like;
    private Integer dislike;

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLike() {
        return like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }
}
