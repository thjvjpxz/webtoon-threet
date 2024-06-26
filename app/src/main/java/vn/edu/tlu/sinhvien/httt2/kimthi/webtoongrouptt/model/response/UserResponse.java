package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.Comments;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.User;

import java.util.List;

public class UserResponse {
    private User user;
//    private List<FollowsComic> followsComic;
//    private List<FollowsStory> followsStory;
    private List<Comments> comments;
//    private List<HistoryComic> comics;

    public User getUser() {
        return user;
    }

//    public List<FollowsComic> getFollowsComic() {
//        return followsComic;
//    }
//
//    public List<FollowsStory> getFollowsStory() {
//        return followsStory;
//    }

    public List<Comments> getComments() {
        return comments;
    }

//    public List<HistoryComic> getComics() {
//        return comics;
//    }
}
