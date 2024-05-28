package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.HomeRepository;

public class HomeViewModel extends ViewModel {
    private HomeRepository homeRepository;
    private LiveData<List<Comic>> comics;
    private MutableLiveData<Boolean> isLoaded;

    public HomeViewModel() {
        isLoaded = new MutableLiveData<>();
        homeRepository = HomeRepository.getInstance();
        isLoaded.setValue(false);
        comics = homeRepository.getComics(() -> isLoaded.setValue(true));
    }

    public LiveData<List<Comic>> getComics() {
        return comics;
    }

    public MutableLiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }
}
