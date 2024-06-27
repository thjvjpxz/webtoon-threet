package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Category;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class HomeResponse {
    private List<Comic> popularComics;
    private List<Comic> recentComics;
    private List<Comic> completedComics;
    private List<Comic> rankingComics;
    private List<Category> categories;

    public List<Comic> getPopularComics() {
        return popularComics;
    }

    public List<Comic> getRecentComics() {
        return recentComics;
    }

    public List<Comic> getCompletedComics() {
        return completedComics;
    }

    public List<Comic> getRankingComics() {
        return rankingComics;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
