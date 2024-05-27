package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;

    public RegisterRequest(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
