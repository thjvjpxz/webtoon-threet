package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class HomeRepository {
    private static volatile HomeRepository instance;
    private ApiService apiService;

    public HomeRepository(Context context) {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public static HomeRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (HomeRepository.class) {
                if (instance == null) {
                    instance = new HomeRepository(context);
                }
            }
        }
        return instance;
    }

    public MutableLiveData<HomeResponse> fetchHomeData(Runnable isLoaded) {
        MutableLiveData<HomeResponse> homeResponseData = new MutableLiveData<>();
        apiService.getComics().enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        HomeResponse homeResponse = response.body();
                        homeResponseData.setValue(homeResponse);
                    }
                }
                isLoaded.run();
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                homeResponseData.setValue(null);
                isLoaded.run();
            }
        });

        return homeResponseData;
    }
}
