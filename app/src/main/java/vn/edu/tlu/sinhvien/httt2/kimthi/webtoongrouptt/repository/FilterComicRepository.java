package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ComicByCategoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FilterComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class FilterComicRepository {
    private static FilterComicRepository instance;
    Context context;
    ApiService apiService;
    public FilterComicRepository(Context context) {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public LiveData<FilterComicResponse> fetchData(int page, int sort, int status, String keyword) {
        MutableLiveData<FilterComicResponse> filterSearchResponse = new MutableLiveData<>();

        apiService.getFilterComics(page, sort, status, keyword).enqueue(new Callback<FilterComicResponse>() {
            @Override
            public void onResponse(Call<FilterComicResponse> call, Response<FilterComicResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        FilterComicResponse dataResponse = response.body();
                        filterSearchResponse.setValue(dataResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<FilterComicResponse> call, Throwable t) {
                filterSearchResponse.setValue(null);
            }
        });
        return filterSearchResponse;
    }
}
