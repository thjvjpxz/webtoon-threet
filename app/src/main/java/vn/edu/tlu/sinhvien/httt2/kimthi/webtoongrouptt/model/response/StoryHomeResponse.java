package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;

public class StoryHomeResponse {
    private List<Story> storiesHot;
    private List<Story> storiesComplete;
    private List<Story> storiesConvert;
    private List<Story> storiesTranslation;

    public StoryHomeResponse() {
    }

    public StoryHomeResponse(List<Story> storiesHot, List<Story> storiesComplete, List<Story> storiesConvert, List<Story> storiesTranslation) {
        this.storiesHot = storiesHot;
        this.storiesComplete = storiesComplete;
        this.storiesConvert = storiesConvert;
        this.storiesTranslation = storiesTranslation;
    }

    public List<Story> getStoriesHot() {
        return storiesHot;
    }

    public void setStoriesHot(List<Story> storiesHot) {
        this.storiesHot = storiesHot;
    }

    public List<Story> getStoriesComplete() {
        return storiesComplete;
    }

    public void setStoriesComplete(List<Story> storiesComplete) {
        this.storiesComplete = storiesComplete;
    }

    public List<Story> getStoriesConvert() {
        return storiesConvert;
    }

    public void setStoriesConvert(List<Story> storiesConvert) {
        this.storiesConvert = storiesConvert;
    }

    public List<Story> getStoriesTranslation() {
        return storiesTranslation;
    }

    public void setStoriesTranslation(List<Story> storiesTranslation) {
        this.storiesTranslation = storiesTranslation;
    }
}
