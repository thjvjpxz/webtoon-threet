package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request;

public class TwitterRequest {
    private String name;
    private String email;
    private String avatar;

    public TwitterRequest(String name, String email, String avatar) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
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
}
