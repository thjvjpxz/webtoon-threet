package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ListTypeComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class ListTypeComicRepository {
    private static ListTypeComicRepository instance;
    Context context;
    ApiService apiService;
    private ListTypeComicRepository() {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public static ListTypeComicRepository getInstance() {
        if (instance == null) {
            synchronized (DetailRepository.class) {
                if (instance == null) {
                    instance = new ListTypeComicRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ListTypeComicResponse> fetchData(String slug, int page, Runnable isLoaded) {
        MutableLiveData<ListTypeComicResponse> responseData = new MutableLiveData<>();

        apiService.getComicsByType(slug, page).enqueue(new Callback<ListTypeComicResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListTypeComicResponse> call, @NonNull Response<ListTypeComicResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ListTypeComicResponse dataResponse = response.body();
                        responseData.setValue(dataResponse);
                    }
                }
                isLoaded.run();
            }

            @Override
            public void onFailure(@NonNull Call<ListTypeComicResponse> call, @NonNull Throwable t) {
                responseData.setValue(null);
                isLoaded.run();
            }
        });
        return responseData;
    }
}
