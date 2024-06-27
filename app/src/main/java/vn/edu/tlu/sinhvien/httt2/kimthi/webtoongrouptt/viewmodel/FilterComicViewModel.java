package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import android.app.Application;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FilterComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.FilterComicRepository;

public class FilterComicViewModel extends AndroidViewModel {
    FilterComicRepository filterComicRepository;
    public FilterComicViewModel(@NonNull Application application) {
        super(application);
        filterComicRepository = new FilterComicRepository(application);
    }

    public LiveData<FilterComicResponse> fetchData(int page, int sort, int status, String keyword) {
        return filterComicRepository.fetchData(page, sort, status, keyword);
    }
}
