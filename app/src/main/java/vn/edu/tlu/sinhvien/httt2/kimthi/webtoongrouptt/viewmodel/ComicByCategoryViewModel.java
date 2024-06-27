package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ComicByCategoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.Paginate;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.ComicByCategoryRepository;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.DetailRepository;

public class ComicByCategoryViewModel extends ViewModel {
    private ComicByCategoryRepository comicByCategoryRepository;
    private MutableLiveData<ComicByCategoryResponse> responseComicByCategory;
    private MutableLiveData<Boolean> isLoaded;
    private int page;
    private String categoryId;

    public ComicByCategoryViewModel(String categoryId, int page) {
        this.page = page;
        this.categoryId = categoryId;
        this.comicByCategoryRepository = ComicByCategoryRepository.getInstance();
        this.isLoaded = new MutableLiveData<>(false);
        this.responseComicByCategory = new MutableLiveData<>();
        fetchData(categoryId, page);
    }

    public LiveData<ComicByCategoryResponse> getResponseComicByCategory() {
        return responseComicByCategory;
    }

    public LiveData<Boolean> getIsLoaded() {
        return isLoaded;
    }

    public void fetchData(String categoryId, int page) {
        isLoaded.setValue(false);
        comicByCategoryRepository.fetchData(categoryId, page, () -> isLoaded.setValue(true))
                .observeForever(new Observer<ComicByCategoryResponse>() {
                    @Override
                    public void onChanged(ComicByCategoryResponse response) {
                        if (response != null) {
                            if (responseComicByCategory.getValue() == null) {
                                responseComicByCategory.setValue(response);
                            } else {
                                ComicByCategoryResponse currentResponse = responseComicByCategory.getValue();
                                currentResponse.getData().getData().addAll(response.getData().getData());
                                responseComicByCategory.setValue(currentResponse);
                            }
                        }
                        isLoaded.setValue(true);
                    }
                });
    }

    public void loadMoreData() {
        page++;
        fetchData(categoryId, page);
    }
}
