package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.DetailStoryRepository;

public class DetailStoryViewModel extends AndroidViewModel {
    private DetailStoryRepository repository;

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<DetailStoryResponse> detailStoryResponse;

    public DetailStoryViewModel(@NonNull Application application) {
        super(application);
        isLoading = new MutableLiveData<>();
        repository = DetailStoryRepository.getInstance(application);
        isLoading.setValue(false);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<DetailStoryResponse> getDetailStoryResponse(int storyId) {
        detailStoryResponse = repository.getDetailStory(storyId, () -> isLoading.setValue(true));
        return detailStoryResponse;
    }
}
