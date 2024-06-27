package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

public class LoginResponse {
    private String token;
    private String avatar;
    private String name;
    private String message;
    private String status;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
