package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.io.Serializable;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;

public class StoriesByTypeResponse implements Serializable {
    private String title;
    private StoriesByType stories;

    public String getTitle() {
        return title;
    }

    public StoriesByType getStories() {
        return stories;
    }

    public class StoriesByType implements Serializable {
        private int current_page;
        private List<Story> data;

        public int getCurrent_page() {
            return current_page;
        }

        public List<Story> getData() {
            return data;
        }
    }
}
