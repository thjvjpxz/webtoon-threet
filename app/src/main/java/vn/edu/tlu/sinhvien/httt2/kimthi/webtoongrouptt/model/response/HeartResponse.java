package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Follow;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.History;

public class HeartResponse {
    private String status;
    private List<Follow> follows;
    private List<History> histories;

    public String getStatus() {
        return status;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public List<History> getHistories() {
        return histories;
    }
}
