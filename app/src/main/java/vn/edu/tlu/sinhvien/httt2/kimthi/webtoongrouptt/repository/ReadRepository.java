package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class ReadRepository {
    private static ReadRepository instance;
    private ApiService apiService;
    private Context context;
    private ReadRepository(Context context) {
        this.context = context.getApplicationContext();
        apiService = ApiClient.getRetrofit(this.context).create(ApiService.class);
    }

    public static ReadRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (ReadRepository.class) {
                if (instance == null) {
                    instance = new ReadRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<ReadComicResponse> fetchChapterData(String chapterId, Runnable isLoaded) {
        MutableLiveData<ReadComicResponse> readComicResponseData = new MutableLiveData<>();

        apiService.getChapterComic(chapterId).enqueue(new Callback<ReadComicResponse>() {
            @Override
            public void onResponse(Call<ReadComicResponse> call, Response<ReadComicResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ReadComicResponse readComicResponse = response.body();
                        readComicResponseData.setValue(readComicResponse);
                    }
                }
                isLoaded.run();
            }

            @Override
            public void onFailure(Call<ReadComicResponse> call, Throwable t) {
                readComicResponseData.setValue(null);
                isLoaded.run();
            }
        });
        return readComicResponseData;
    }
}
