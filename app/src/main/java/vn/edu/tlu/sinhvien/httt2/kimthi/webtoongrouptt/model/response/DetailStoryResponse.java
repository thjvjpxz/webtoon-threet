package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;

public class DetailStoryResponse {
    private Story story;
    private List<Chapter> chapters;
    private List<Comment> comments;
    private List<String> history;

    public DetailStoryResponse() {}

    public DetailStoryResponse(Story story, List<Chapter> chapters, List<Comment> comments, List<String> history) {
        this.story = story;
        this.chapters = chapters;
        this.comments = comments;
        this.history = history;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }
}
