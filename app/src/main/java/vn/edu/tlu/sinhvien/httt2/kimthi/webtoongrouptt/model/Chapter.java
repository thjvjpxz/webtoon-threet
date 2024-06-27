package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class Chapter {
    private Integer id;
    private String name;
    private String title;
    private String image;
    private Integer prevChap;
    private Integer nextChap;
    private Integer comic_id;
    private String created_at;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrevChap() {
        return prevChap;
    }

    public Integer getNextChap() {
        return nextChap;
    }

    public Integer getComic_id() {
        return comic_id;
    }

    public String getCreated_at() {
        return created_at;
    }
}
