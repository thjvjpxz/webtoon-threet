package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view;

import java.util.List;

public class Stickers {
    private String name;
    private List<String> urls;

    public Stickers(String name, List<String> urls) {
        this.name = name;
        this.urls = urls;
    }

    public String getName() {
        return name;
    }

    public List<String> getUrls() {
        return urls;
    }
}
