package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class DetailResponse {
    private Comic comic;
    private String status;
    private List<Chapter> chapters;
    private List<Comic> relatedComics;

    public Comic getComic() {
        return comic;
    }

    public String getStatus() {
        return status;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public List<Comic> getRelatedComics() {
        return relatedComics;
    }
}
