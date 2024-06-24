package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private int id;
    private String name;
    private String title;
    private String slug;
    private String chapterNumber;
    private String updated_at;

    public Chapter() {
    }

    public Chapter(int id, String name, String title, String slug, String chapterNumber, String updated_at) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.slug = slug;
        this.chapterNumber = chapterNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
