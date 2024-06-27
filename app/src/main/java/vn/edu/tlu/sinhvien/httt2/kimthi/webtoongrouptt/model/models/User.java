package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String avatar;
    private String role;
    private String created_at;
    private String updated_at;
    private Integer exp;
    private Integer road_id;
    private NextLevel level;
    private NextLevel nextLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getRoad_id() {
        return road_id;
    }

    public void setRoad_id(Integer road_id) {
        this.road_id = road_id;
    }

    public NextLevel getLevel() {
        return level;
    }

    public void setLevel(NextLevel level) {
        this.level = level;
    }

    public NextLevel getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(NextLevel nextLevel) {
        this.nextLevel = nextLevel;
    }
}
