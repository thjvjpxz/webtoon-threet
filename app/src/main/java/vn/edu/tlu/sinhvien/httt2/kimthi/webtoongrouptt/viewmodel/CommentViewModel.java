package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.CommentRepository;

public class CommentViewModel extends ViewModel {
    private final CommentRepository commentRepository;

    public CommentViewModel(Context context) {
        commentRepository = CommentRepository.getInstance(context);
    }

    public LiveData<Boolean> comment(Integer comicId, Integer chapterId, String content) {
        return commentRepository.comment(comicId, chapterId, content);
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
