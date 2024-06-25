package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.FollowRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
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

    public LiveData<Boolean> followComic(String comicId, String type) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        FollowRequest followRequest = new FollowRequest(comicId, type);

        Call<BaseResponse> call = apiService.follow(followRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if(baseResponse != null) {
                        Toast.makeText(context, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (Objects.equals(baseResponse.getMessage(), "Theo dõi thành công")) {
                            result.setValue(true);
                        } else {
                            result.setValue(false);
                        }
                    }
                } else {
                    Toast.makeText(context, "Theo dõi thất bại", Toast.LENGTH_SHORT).show();
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                result.setValue(false);
            }
        });
        return result;
    }
}
