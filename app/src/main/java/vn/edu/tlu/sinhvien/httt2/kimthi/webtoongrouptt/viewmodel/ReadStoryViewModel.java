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

    public ReadStoryViewModel(@NonNull Application application) {
        super(application);
        isLoading = new MutableLiveData<>();
        readStoryRepository = ReadStoryRepository.getInstance(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<ReadStoryResponse> getReadStoryResponse(int idChapter) {
        isLoading.setValue(true);

        return (MutableLiveData<ReadStoryResponse>) readStoryRepository.getContentStory(idChapter, () -> {
            isLoading.setValue(false);
        });
    }

}
