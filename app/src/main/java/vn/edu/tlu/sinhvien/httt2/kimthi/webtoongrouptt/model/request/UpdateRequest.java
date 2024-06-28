package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request;

import okhttp3.MultipartBody;

public class UpdateRequest {
    private String name;
    private String email;
    private Integer road_id;
    private String uriAvatar;

    public UpdateRequest(String name, String email, Integer road_id, String uriAvatar) {
        this.name = name;
        this.email = email;
        this.road_id = road_id;
        this.uriAvatar = uriAvatar;
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

    public Integer getRoad_id() {
        return road_id;
    }

    public void setRoad_id(Integer road_id) {
        this.road_id = road_id;
    }

    public String getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(String uriAvatar) {
        this.uriAvatar = uriAvatar;
    }
}
