package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.HomeRepository;

public class HomeViewModel extends ViewModel {
    private HomeRepository homeRepository;
    private MutableLiveData<HomeResponse> responseHome;
    private MutableLiveData<Boolean> isLoaded;

    public HomeViewModel(Context context) {
        isLoaded = new MutableLiveData<>();
        homeRepository = new HomeRepository(context);
        homeRepository = HomeRepository.getInstance(context);
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
