package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.io.Serializable;
import java.util.List;

public class ReadStoryResponse implements Serializable {
    private Chapter chapter;

    public ReadStoryResponse() {
    }

    public ReadStoryResponse(Chapter chapter) {
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public class Chapter implements Serializable {
        private int id;
        private String name;
        private String title;
        private String content;
        private int story_id;

        private Chapter() {
        }

        public Chapter(int id, String name, String title, String content, int story_id) {
            this.id = id;
            this.name = name;
            this.title = title;
            this.content = content;
            this.story_id = story_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStory_id() {
            return story_id;
        }

        public void setStory_id(int story_id) {
            this.story_id = story_id;
        }
    }
}
