package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Category;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class ComicByCategoryResponse {
    private String status;
    private Category category;
    private Paginate data;

    public String getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }

    public Paginate getData() {
        return data;
    }
}
