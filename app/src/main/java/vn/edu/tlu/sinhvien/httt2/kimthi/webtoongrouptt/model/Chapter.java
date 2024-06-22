package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class Chapter {
    private Integer id;
    private String name;
    private String title;
    private String image;
    private Integer prevChap;
    private Integer nextChap;

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
}
