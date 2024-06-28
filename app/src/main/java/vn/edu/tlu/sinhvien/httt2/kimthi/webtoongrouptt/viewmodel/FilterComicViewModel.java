package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;

import java.util.HashMap;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FilterComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.FilterComicRepository;

public class FilterComicViewModel extends AndroidViewModel {
    private FilterComicRepository filterComicRepository;
    private HashMap<String, MutableLiveData<FilterComicResponse>> dataMap;

    public FilterComicViewModel(@NonNull Application application) {
        super(application);
        filterComicRepository = new FilterComicRepository(application);
        dataMap = new HashMap<>();
    }

    public LiveData<FilterComicResponse> fetchDataComics(int page, int sort, int status, String keyword) {
        String key = page + "-" + sort + "-" + status + "-" + keyword;
        MutableLiveData<FilterComicResponse> data = dataMap.get(key);
        if (data == null) {
            data = (MutableLiveData<FilterComicResponse>) filterComicRepository.fetchDataComics(page, sort, status, keyword);
            dataMap.put(key, data);
        }
        return data;
    }

    public LiveData<FilterComicResponse> fetchDataStories(int page, String keyword) {
        String key = page + "-" + keyword;
        MutableLiveData<FilterComicResponse> data = dataMap.get(key);
        if (data == null) {
            data = (MutableLiveData<FilterComicResponse>) filterComicRepository.fetchDataStories(page, keyword);
            dataMap.put(key, data);
        }
        return data;
    }

    public void refreshData(int page, int sort, int status, String keyword) {
        String key = page + "-" + sort + "-" + status + "-" + keyword;
        MutableLiveData<FilterComicResponse> data = (MutableLiveData<FilterComicResponse>) filterComicRepository.fetchDataComics(page, sort, status, keyword);
        dataMap.put(key, data);
    }
}