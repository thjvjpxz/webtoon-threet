package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.FollowRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class DetailStoryRepository {
    private static DetailStoryRepository instance;
    private ApiService apiService;

    private DetailStoryRepository(Context context) {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public static DetailStoryRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (DetailStoryRepository.class) {
                if (instance == null) {
                    instance = new DetailStoryRepository(context);
                }
            }
        }
        return instance;
    }

    public MutableLiveData<DetailStoryResponse> getDetailStory(int storyId, Runnable isLoading) {
        MutableLiveData<DetailStoryResponse> data = new MutableLiveData<>();
        apiService.getDetailStory(storyId).enqueue(new Callback<DetailStoryResponse>() {
            @Override
            public void onResponse(Call<DetailStoryResponse> call,
                                   Response<DetailStoryResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                isLoading.run();
            }

            @Override
            public void onFailure(Call<DetailStoryResponse> call, Throwable t) {
                Log.d("DetailStoryRepository", "onFailure: " + t.getMessage());
                data.setValue(null);
                isLoading.run();
            }
        });
        return data;
    }

    public MutableLiveData<BaseResponse> followStory(String storyId) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        FollowRequest followRequest = new FollowRequest(storyId, "story");
        apiService.follow(followRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("DetailStoryRepository", "onFailure: " + t.getMessage());
                data.setValue(null);
            }
        });

        return data;
    }
}
