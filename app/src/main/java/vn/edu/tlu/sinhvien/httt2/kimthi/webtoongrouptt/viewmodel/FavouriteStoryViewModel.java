package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.FavouriteStoryRepository;

public class FavouriteStoryViewModel extends AndroidViewModel {
    private FavouriteStoryRepository repo;
    private MutableLiveData<Boolean> isLoading;

    public FavouriteStoryViewModel(@NonNull Application application) {
        super(application);
        repo = FavouriteStoryRepository.getInstance(application);
        isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<FavouriteStoryResponse> getFavouriteStory() {
        isLoading.setValue(true);
        return (MutableLiveData<FavouriteStoryResponse>) repo.getFavouriteStory(() -> isLoading.setValue(false));
    }
}
