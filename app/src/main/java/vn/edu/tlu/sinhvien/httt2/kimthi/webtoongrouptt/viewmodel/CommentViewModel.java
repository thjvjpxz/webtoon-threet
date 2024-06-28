package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.CommentRepository;

public class CommentViewModel extends AndroidViewModel {
    private final CommentRepository commentRepository;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        commentRepository = CommentRepository.getInstance(application);
    }


    public LiveData<Boolean> commentComic(Integer comicId, Integer chapterId, String content) {
        return commentRepository.comment(comicId, null, chapterId, content, "comic");
    }

    public LiveData<Boolean> commentStory(Integer storyId, Integer chapterId, String content) {
        return commentRepository.comment(null , storyId, chapterId, content, "story");
    }

    public LiveData<Boolean> likeComment(String commentId) {
        return commentRepository.likeComment(commentId);
    }

    public LiveData<Boolean> dislikeComment(String commentId) {
        return commentRepository.dislikeComment(commentId);
    }

    public LiveData<Boolean> reportComment(String commentId) {
        return commentRepository.reportComment(commentId);
    }
}
