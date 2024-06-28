package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

import java.util.List;

public class Comic {
    private Integer id;
    private String name;
    private String origin_name;
    private String slug;
    private String content;
    private String thumbnail;
    private Integer total_views;
    private Float rating;
    private List<Category> categories;
    private List<Author> authors;
    private String status;
    private Integer chapters_count;
    private Chapter chapter;
    private Integer views;
    private String date_updated;

    public Integer getViews() {
        return views;
    }

    public Integer getChapters_count() {
        return chapters_count;
    }

    public Float getRating() {
        return rating;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Integer getTotal_views() {
        return total_views;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getStatus() {
        return status;
    }

    public String getDate_updated() {
        return date_updated;
    }
    public Chapter getChapter() {
        return chapter;
    }

    public String getSlug() {
        return slug;
    }
}
