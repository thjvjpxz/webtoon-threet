package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Image;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.User;

public class ReadComicResponse {
    private String status;
    private Chapter chapter;
    private List<Image> images;
    private Boolean follow;
    private User user;
    private List<Comment> comments;

    public String getStatus() {
        return status;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public List<Image> getImages() {
        return images;
    }

    public Boolean getFollow() {
        return follow;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
