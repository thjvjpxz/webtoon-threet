package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class Comments {
    private Integer id;
    private Integer user_id;
    private Integer story_id;
    private Integer comic_id;
    private Integer like;
    private Integer dislike;
    private Integer report;
    private Integer chapter_id;
    private String content;
    private String created_at;
    private String updated_at;
    private Comic comic;
    private Comic story;

    public Comments(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getStory_id() {
        return story_id;
    }

    public void setStory_id(Integer story_id) {
        this.story_id = story_id;
    }

    public Integer getComic_id() {
        return comic_id;
    }

    public void setComic_id(Integer comic_id) {
        this.comic_id = comic_id;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(Integer chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public Comic getStory() {
        return story;
    }

    public void setStory(Comic story) {
        this.story = story;
    }
}
