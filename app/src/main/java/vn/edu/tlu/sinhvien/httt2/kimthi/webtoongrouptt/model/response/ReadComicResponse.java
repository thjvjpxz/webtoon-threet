package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Image;

public class ReadComicResponse {
    private String status;
    private Chapter chapter;
    private List<Image> images;

    public String getStatus() {
        return status;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public List<Image> getImages() {
        return images;
    }
}
