package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.SaveHistoryRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ResponseBase;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class ReadStoryRepository {
    private static ReadStoryRepository instance;
    private ApiService apiServiceHeader;
    private ApiService apiService;

    private ReadStoryRepository(Context context) {
        apiServiceHeader = ApiClient.getRetrofitHeader(context).create(ApiService.class);
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public static ReadStoryRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (ReadStoryRepository.class) {
                if (instance == null) {
                    instance = new ReadStoryRepository(context);
                }
            }
        }
        return instance;
    }

    private LiveData<ResponseBase> saveHistory(SaveHistoryRequest saveHistoryRequest) {
        MutableLiveData<ResponseBase> mutableLiveData = new MutableLiveData<>();
        apiServiceHeader.saveHistory(saveHistoryRequest).enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

    public LiveData<ReadStoryResponse> getContentStory(String slug_story, String slug_chapter, Runnable isLoading) {
        MutableLiveData<ReadStoryResponse> mutableLiveData = new MutableLiveData<>();
        apiService.getContentStory(slug_story, slug_chapter).enqueue(new Callback<ReadStoryResponse>() {
            @Override
            public void onResponse(Call<ReadStoryResponse> call,
                                   Response<ReadStoryResponse> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                    int id_story = response.body().getChapter().getStory_id();
                    int id_chapter = response.body().getChapter().getId();
                    String type = "story";
                    SaveHistoryRequest saveHistoryRequest = new SaveHistoryRequest(
                            id_story,
                            type,
                            id_chapter
                    );
                    saveHistory(saveHistoryRequest);
                }
                isLoading.run();
            }

            @Override
            public void onFailure(Call<ReadStoryResponse> call, Throwable t) {
                Log.d("ReadStoryRepository", "onFailure: " + t.getMessage());
                mutableLiveData.setValue(null);
                isLoading.run();
            }
        });

        return mutableLiveData;
    }
}
