package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.FollowRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class DetailRepository {
    private static DetailRepository instance;
    private ApiService apiService;
    private Context context;
    private DetailRepository(Context context) {
        this.context = context.getApplicationContext();
        apiService = ApiClient.getRetrofitHeader(this.context).create(ApiService.class);
    }

    public static DetailRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (DetailRepository.class) {
                if (instance == null) {
                    instance = new DetailRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<DetailResponse> fetchDetailData(String comicId, Runnable isLoaded) {
        MutableLiveData<DetailResponse> detailResponseData = new MutableLiveData<>();

        apiService.getDetailComic(comicId).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        DetailResponse detailResponse = response.body();
                        detailResponseData.setValue(detailResponse);
                    }
                }
                isLoaded.run();
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                detailResponseData.setValue(null);
                isLoaded.run();
            }
        });
        return detailResponseData;
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
                        result.setValue(true);
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
