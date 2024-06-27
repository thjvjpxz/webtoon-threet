package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class FavouriteStoryRepository {
    private static FavouriteStoryRepository instance;
    private ApiService apiService;

    private FavouriteStoryRepository(Context context) {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public static FavouriteStoryRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (FavouriteStoryRepository.class) {
                if (instance == null) {
                    instance = new FavouriteStoryRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<FavouriteStoryResponse> getFavouriteStory(Runnable isLoading) {
        MutableLiveData<FavouriteStoryResponse> data = new MutableLiveData<>();

        apiService.getListFavoriteStory().enqueue(new Callback<FavouriteStoryResponse>() {
            @Override
            public void onResponse(Call<FavouriteStoryResponse> call,
                                   Response<FavouriteStoryResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                isLoading.run();
            }

            @Override
            public void onFailure(Call<FavouriteStoryResponse> call, Throwable t) {
                isLoading.run();
            }
        });

        return data;
    }
}
