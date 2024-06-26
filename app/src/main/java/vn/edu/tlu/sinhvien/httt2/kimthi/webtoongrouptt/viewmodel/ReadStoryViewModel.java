package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.ReadStoryRepository;

public class ReadStoryViewModel extends AndroidViewModel {
    private ReadStoryRepository readStoryRepository;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<ReadStoryResponse> readStoryResponse;

    public ReadStoryViewModel(@NonNull Application application) {
        super(application);
        isLoading = new MutableLiveData<>();
        readStoryResponse = new MutableLiveData<>();
        readStoryRepository = ReadStoryRepository.getInstance(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<ReadStoryResponse> getReadStoryResponse(String slug_story,
                                                                   String slug_chapter) {
        isLoading.setValue(true);

        readStoryResponse =
                (MutableLiveData<ReadStoryResponse>) readStoryRepository.getContentStory(slug_story, slug_chapter, () -> {
                    isLoading.setValue(false);
                });
        return readStoryResponse;
    }

}
