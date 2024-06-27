package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request;

public class ForgotRequest {
    private String email;

    public ForgotRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
