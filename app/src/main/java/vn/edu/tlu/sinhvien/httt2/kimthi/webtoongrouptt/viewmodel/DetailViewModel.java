package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.DetailRepository;

public class DetailViewModel extends ViewModel {
    private DetailRepository detailRepository;
    private LiveData<DetailResponse> responseDetail;
    private MutableLiveData<Boolean> isLoaded;
    public DetailViewModel(String comicId, Context context){
        isLoaded = new MutableLiveData<>();
        detailRepository = DetailRepository.getInstance(context);
        isLoaded.setValue(false);
        responseDetail = detailRepository.fetchDetailData(comicId, () -> isLoaded.setValue(true));
    }
    public LiveData<DetailResponse> fetchDetailData() {
        return responseDetail;
    }
    public MutableLiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }
    public LiveData<Boolean> followComic(String comicId, String type) {
        return detailRepository.followComic(comicId, type);
    }
}
