package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

public class User {
    private int id;
    private String name;
    String email;
    String avatar;
    int exp;
    int road_id;
    Level level;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getExp() {
        return exp;
    }

    public int getRoad_id() {
        return road_id;
    }

    public Level getLevel() {
        return level;
    }
}
