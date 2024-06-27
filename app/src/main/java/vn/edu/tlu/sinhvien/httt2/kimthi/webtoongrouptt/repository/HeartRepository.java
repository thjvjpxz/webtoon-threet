package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HeartResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class HeartRepository {
    private static HeartRepository instance;
    ApiService apiService;
    Context context;
    public HeartRepository() {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public static HeartRepository getInstance() {
        if (instance == null) {
            synchronized (HeartRepository.class) {
                if (instance == null) {
                    instance = new HeartRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<HeartResponse> fetchData(Runnable isLoaded) {
        MutableLiveData<HeartResponse> responseData = new MutableLiveData<>();

        apiService.getListFavourite().enqueue(new Callback<HeartResponse>() {
            @Override
            public void onResponse(@NonNull Call<HeartResponse> call, @NonNull Response<HeartResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        HeartResponse heartResponse = response.body();
                        responseData.setValue(heartResponse);
                    }
                }
                isLoaded.run();
            }

            @Override
            public void onFailure(@NonNull Call<HeartResponse> call, @NonNull Throwable t) {
                responseData.setValue(null);
                isLoaded.run();
            }
        });
        return responseData;
    }
}
