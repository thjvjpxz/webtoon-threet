package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.UserResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class UserRepository {
    private static volatile UserRepository instance;
    ApiService apiService;

    public UserRepository(Context context) {
        apiService = ApiClient.getRetrofitHeader(context).create(ApiService.class);
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository(context);
                }
            }
        }
        return instance;
    }

    public MutableLiveData<UserResponse> fetchUserData() {
        MutableLiveData<UserResponse> userResponseData = new MutableLiveData<>();
        apiService.getUser().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        UserResponse userResponse = response.body();
                        userResponseData.setValue(userResponse);
                    } else {
                        userResponseData.setValue(null);
                    }
                } else {
                    userResponseData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                userResponseData.setValue(null);
            }
        });

        return userResponseData;
    }

    public LiveData<ResponseBody> LogOut () {
        MutableLiveData<ResponseBody> logoutResponseData = new MutableLiveData<>();
        apiService.logOut().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    logoutResponseData.setValue(response.body());
                } else {
                    logoutResponseData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                logoutResponseData.setValue(null);
            }
        });

        return logoutResponseData;
    }

}
