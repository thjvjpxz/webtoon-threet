package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request;

import java.io.Serializable;

public class SaveHistoryRequest implements Serializable {
    private int id;
    private String type;
    private int chapter_id;

    public SaveHistoryRequest() {
    }

    public SaveHistoryRequest(int id, String type, int chapter_id) {
        this.id = id;
        this.type = type;
        this.chapter_id = chapter_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }
}
