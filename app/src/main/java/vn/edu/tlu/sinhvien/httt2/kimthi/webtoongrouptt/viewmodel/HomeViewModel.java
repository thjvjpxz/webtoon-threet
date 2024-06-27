package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.HomeRepository;

public class HomeViewModel extends AndroidViewModel{
    private HomeRepository homeRepository;
    private MutableLiveData<HomeResponse> responseHome;
    private MutableLiveData<Boolean> isLoaded;

    public HomeViewModel(@NotNull Application application) {
        super(application);
        isLoaded = new MutableLiveData<>();
        homeRepository = HomeRepository.getInstance(application);
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
