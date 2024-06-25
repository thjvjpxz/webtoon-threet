package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ComicByCategoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class ComicByCategoryRepository {
    private static ComicByCategoryRepository instance;
    Context context;
    ApiService apiService;
    private ComicByCategoryRepository() {
        apiService = ApiClient.getRetrofit(context).create(ApiService.class);
    }

    public static ComicByCategoryRepository getInstance() {
        if (instance == null) {
            synchronized (DetailRepository.class) {
                if (instance == null) {
                    instance = new ComicByCategoryRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ComicByCategoryResponse> fetchData(String categoryId, int page, Runnable isLoaded) {
        MutableLiveData<ComicByCategoryResponse> detailResponseData = new MutableLiveData<>();

        apiService.getComicByCategory(categoryId, page).enqueue(new Callback<ComicByCategoryResponse>() {
            @Override
            public void onResponse(Call<ComicByCategoryResponse> call, Response<ComicByCategoryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ComicByCategoryResponse dataResponse = response.body();
                        detailResponseData.setValue(dataResponse);
                    }
                }
                isLoaded.run();
            }

            @Override
            public void onFailure(Call<ComicByCategoryResponse> call, Throwable t) {
                detailResponseData.setValue(null);
                isLoaded.run();
            }
        });
        return detailResponseData;
    }
}
