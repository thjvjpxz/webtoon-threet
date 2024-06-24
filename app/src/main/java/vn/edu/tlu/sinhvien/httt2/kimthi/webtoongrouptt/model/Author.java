package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

import java.io.Serializable;

public class Author implements Serializable {
    private int id;
    private String name;
    private String slug;

    public Author(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
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
}
