package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request;

public class CommentRequest {
    String content;
    String type;
    Integer chapter_id;
    Integer comic_id;
    Integer story_id;
    Integer id;

    public CommentRequest(String content, String type, Integer chapter_id, Integer comic_id, Integer story_id) {
        this.content = content;
        this.type = type;
        this.chapter_id = chapter_id;
        this.comic_id = comic_id;
        this.story_id = story_id;
    }
}
