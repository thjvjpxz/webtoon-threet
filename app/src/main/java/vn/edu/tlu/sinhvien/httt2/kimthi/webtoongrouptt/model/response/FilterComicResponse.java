package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Category;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class FilterComicResponse {
    private Paginate comics;
    public Paginate getComics() {
        return comics;
    }

    public void setComics(Paginate comics) {
        this.comics = comics;
    }
}
