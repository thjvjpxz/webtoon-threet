package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class Story {
    private int id;
    private String name;
    private String slug;
    private String thumbnail;
    private int total_views;
    private Chapter chapter;

    public Story(int id, String name, String slug, String thumbnail, int total_views, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.thumbnail = thumbnail;
        this.total_views = total_views;
        this.chapter = chapter;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getTotal_views() {
        return total_views;
    }

    public void setTotal_views(int total_views) {
        this.total_views = total_views;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public class Chapter {
        private String name;
        private String slug;
        private String title;

        public Chapter() {}

        public Chapter(String name, String slug, String title) {
            this.name = name;
            this.slug = slug;
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
