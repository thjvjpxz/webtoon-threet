package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class History {
    private int id;
    private Comic comic;
    private Chapter lastChapter;
    private Chapter lastRead;

    public int getId() {
        return id;
    }

    public Comic getComic() {
        return comic;
    }

    public Chapter getLastChapter() {
        return lastChapter;
    }

    public Chapter getLastRead() {
        return lastRead;
    }
}
