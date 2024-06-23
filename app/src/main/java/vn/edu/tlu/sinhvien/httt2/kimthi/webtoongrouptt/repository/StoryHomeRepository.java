package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.StoryHomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class StoryHomeRepository {
    private static StoryHomeRepository instance;
    private ApiService apiService;

    public StoryHomeRepository(Context context) {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public static StoryHomeRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (StoryHomeRepository.class) {
                if (instance == null) {
                    instance = new StoryHomeRepository(context);
                }
            }
        }
        return instance;
    }

    public MutableLiveData<StoryHomeResponse> fetchStoryHomeData(Runnable isLoaded) {
        MutableLiveData<StoryHomeResponse> storyHomeResponseData = new MutableLiveData<>();

        apiService.getStories().enqueue(new Callback<StoryHomeResponse>() {
            @Override
            public void onResponse(Call<StoryHomeResponse> call,
                                   Response<StoryHomeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        StoryHomeResponse storyHomeResponse = new StoryHomeResponse();
                        storyHomeResponse.setStoriesHot(response.body().getStoriesHot());
                        storyHomeResponse.setStoriesComplete(response.body().getStoriesComplete());
                        storyHomeResponse.setStoriesTranslation(response.body().getStoriesTranslation());
                        storyHomeResponse.setStoriesConvert(response.body().getStoriesConvert());
                        storyHomeResponseData.setValue(storyHomeResponse);
                    }
                    isLoaded.run();
                }
            }

            @Override
            public void onFailure(Call<StoryHomeResponse> call, Throwable t) {
                storyHomeResponseData.setValue(null);
                Log.d("StoryHomeRepository", "onFailure: " + t.getMessage());
                isLoaded.run();
            }
        });

        return storyHomeResponseData;
    }
}
