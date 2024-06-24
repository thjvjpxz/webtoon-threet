package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;

public class DetailStoryResponse {
    private Story story;
    private List<Chapter> chapters;

    public DetailStoryResponse() {}

    public DetailStoryResponse(Story story, List<Chapter> chapters) {
        this.story = story;
        this.chapters = chapters;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
