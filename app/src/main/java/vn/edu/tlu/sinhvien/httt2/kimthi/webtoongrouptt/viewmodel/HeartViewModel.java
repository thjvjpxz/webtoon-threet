package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HeartResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.HeartRepository;

public class HeartViewModel extends ViewModel {
    private HeartRepository heartRepository;
    private LiveData<HeartResponse> responseHeart;
    private MutableLiveData<Boolean> isLoaded;

    public HeartViewModel() {
        isLoaded = new MutableLiveData<>();
        heartRepository = HeartRepository.getInstance();
        isLoaded.setValue(false);
        responseHeart = heartRepository.fetchData(() -> isLoaded.postValue(true));
    }

    public LiveData<HeartResponse> fetchData() {
        return responseHeart;
    }

    public MutableLiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }
}
