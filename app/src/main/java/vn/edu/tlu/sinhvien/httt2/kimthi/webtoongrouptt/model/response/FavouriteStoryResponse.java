package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.io.Serializable;
import java.util.List;

public class FavouriteStoryResponse implements Serializable {
    private List<Follow> follows;

    private List<History> histories;

    public List<Follow> getFollows() {
        return follows;
    }

    public List<History> getHistories() {
        return histories;
    }

    public class Follow {
        private int id;
        private Story story;

        public int getId() {
            return id;
        }

        public Story getStory() {
            return story;
        }
    }

    public class Story {
        private int id;
        private String name;
        private String slug;
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSlug() {
            return slug;
        }
    }

    public class History {
        private int id;
        private Chapter lastChapter;
        private Chapter lastRead;
        private Story story;

        public int getId() {
            return id;
        }

        public Chapter getLastChapter() {
            return lastChapter;
        }

        public Chapter getLastRead() {
            return lastRead;
        }

        public Story getStory() {
            return story;
        }
    }

    public class Chapter {
        private int id;
        private String name;
        private String title;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }
    }
}
