package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.StoryHomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.StoryHomeRepository;

public class StoryHomeViewModel extends AndroidViewModel {
    private StoryHomeRepository storyHomeRepository;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<StoryHomeResponse> storyHomeResponse;
    public StoryHomeViewModel(@NonNull Application application) {
        super(application);
        isLoading = new MutableLiveData<>();
        storyHomeRepository = StoryHomeRepository.getInstance(application);
        isLoading.setValue(false);
        storyHomeResponse = storyHomeRepository.fetchStoryHomeData(() -> isLoading.setValue(true));
    }

    public MutableLiveData<StoryHomeResponse> fetchStoryHomeData() {
        return storyHomeResponse;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
