package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class Paginate {
    private Integer current_page;
    private List<Comic> data;
    private Integer last_page;

    public Integer getCurrent_page() {
        return current_page;
    }

    public List<Comic> getData() {
        return data;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public void setData(List<Comic> data) {
        this.data = data;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }
}
