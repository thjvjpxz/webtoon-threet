package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models;

import java.io.Serializable;
public class NextLevel implements Serializable {
    private Integer id;
    private String level;
    private Integer chap;
    private String image;
    private String style;
    private String created_at;
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getChap() {
        return chap;
    }

    public void setChap(Integer chap) {
        this.chap = chap;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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
}
