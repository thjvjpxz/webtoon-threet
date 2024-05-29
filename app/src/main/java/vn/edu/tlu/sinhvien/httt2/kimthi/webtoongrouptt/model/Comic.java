package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class Comic {
    private Integer id;
    private String name;
    private String origin_name;
    private String content;
    private String thumbnail;
    private Integer total_views;
    private Float rating;
    private Integer total_chapters;

    public Float getRating() {
        return rating;
    }

    public Integer getTotal_chapters() {
        return total_chapters;
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
}
