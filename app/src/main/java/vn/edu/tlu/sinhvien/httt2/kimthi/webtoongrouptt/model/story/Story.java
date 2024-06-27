package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story;

import java.io.Serializable;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Author;

public class Story implements Serializable {
    private int id;
    private String name;
    private String slug;
    private String status;
    private String content;
    private List<Author> authors;
    private List<Category> categories;
    private String thumbnail;
    private int total_views;
    private float rating;
    private Chapter chapter;
    private String origin_name;

    public Story(int id, String name, String slug, String status, String content,
                 List<Author> authors, List<Category> categories, String thumbnail,
                 int total_views, float rating, Chapter chapter, String origin_name) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.status = status;
        this.content = content;
        this.authors = authors;
        this.categories = categories;
        this.thumbnail = thumbnail;
        this.total_views = total_views;
        this.rating = rating;
        this.chapter = chapter;
        this.origin_name = origin_name;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }

    public class Chapter implements Serializable {
        private String name;
        private String slug;
        private String title;

        public Chapter() {
        }

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
