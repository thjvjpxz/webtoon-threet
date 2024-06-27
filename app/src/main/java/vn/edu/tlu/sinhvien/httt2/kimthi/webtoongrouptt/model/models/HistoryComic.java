package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class HistoryComic {
    private Integer id;
    private Integer user_id;
    private Integer comic_id;
    private Integer story_id;
    private Integer chapterComics_id;
    private String chapterStories_id;
    private String created_at;
    private String updated_at;
    private Comic comic;
    private Chapter chapter;
}
