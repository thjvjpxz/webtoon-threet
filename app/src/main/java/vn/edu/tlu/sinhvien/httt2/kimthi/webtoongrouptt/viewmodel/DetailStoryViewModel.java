package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.FollowRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Chapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.DetailStoryRepository;

public class DetailStoryViewModel extends AndroidViewModel {
    private DetailStoryRepository repository;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> hasComments;
    private MutableLiveData<DetailStoryResponse> detailStoryResponse;

    public DetailStoryViewModel(@NonNull Application application) {
        super(application);
        isLoading = new MutableLiveData<>();
        hasComments = new MutableLiveData<>();
        detailStoryResponse = new MutableLiveData<>();
        repository = DetailStoryRepository.getInstance(application);
        isLoading.setValue(false);
        hasComments.setValue(false);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getHasComments() {
        return hasComments;
    }

    public LiveData<DetailStoryResponse> getDetailStoryResponse(int storyId) {
        isLoading.setValue(true);
        MutableLiveData<DetailStoryResponse> response = repository.getDetailStory(storyId,
                () -> isLoading.setValue(false));

        response.observeForever(detailStory -> {
            isLoading.setValue(false);
            detailStoryResponse.setValue(detailStory);
            if (detailStory != null && detailStory.getComments() != null && !detailStory.getComments().isEmpty()) {
                hasComments.setValue(true);
            } else {
                hasComments.setValue(false);
            }
        });

        return detailStoryResponse;
    }

    public MutableLiveData<List<Chapter>> getChapters(int storyId) {
        return repository.getChapterByStoryId(storyId);
    }

    public MutableLiveData<Story> getStoryById(int storyId) {
        return repository.getStoryById(storyId);
    }

    public LiveData<BaseResponse> followStory(String storyId) {
        return repository.followStory(storyId);
    }
}
