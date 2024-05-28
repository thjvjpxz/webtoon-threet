package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.HomeRepository;

public class HomeViewModel extends ViewModel {
    private HomeRepository homeRepository;
    private LiveData<HomeResponse> responseHome;
    private MutableLiveData<Boolean> isLoaded;

    public HomeViewModel() {
        isLoaded = new MutableLiveData<>();
        homeRepository = HomeRepository.getInstance();
        isLoaded.setValue(false);
        responseHome = homeRepository.fetchHomeData(() -> isLoaded.setValue(true));
    }

    public LiveData<HomeResponse> fetchHomeData() {
        return responseHome;
    }

    public MutableLiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }
}
