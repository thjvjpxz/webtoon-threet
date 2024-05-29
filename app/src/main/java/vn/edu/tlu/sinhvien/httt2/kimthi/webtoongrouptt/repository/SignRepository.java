package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.ForgotRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ForgotResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.GoogleResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class SignRepository {
    private ApiService apiService;
    private static SignRepository instance;
    private Context context;

    private SignRepository(Context context) {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        this.context = context;
    }

    public static SignRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (SignRepository.class) {
                if (instance == null) {
                    instance = new SignRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<LoginResponse> loginUser(LoginRequest request) {
        MutableLiveData<LoginResponse> loginResponseData = new MutableLiveData<>();
        apiService.loginUser(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    SharedPrefManager share = SharedPrefManager.getInstance(context);
                    share.saveToken(response.body().getToken());
                    share.saveAvatar(response.body().getAvatar());
                    share.saveName(response.body().getName());
                    loginResponseData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("API_CALL", "API call failed: " + t.getMessage());
                loginResponseData.setValue(null);
            }
        });
        return loginResponseData;
    }

    public LiveData<GoogleResponse> loginGoogle(GoogleRequest request) {
        MutableLiveData<GoogleResponse> loginResponseData = new MutableLiveData<>();
        apiService.loginGoogle(request).enqueue(new Callback<GoogleResponse>() {
            @Override
            public void onResponse(Call<GoogleResponse> call, Response<GoogleResponse> response) {
                if (response.isSuccessful()) {
                    GoogleResponse googleResponse = response.body();
                    assert googleResponse != null;
                    SharedPrefManager share = SharedPrefManager.getInstance(context);
                    share.saveToken(googleResponse.getToken());
                    share.saveAvatar(googleResponse.getAvatar());
                    share.saveName(googleResponse.getName());
                    loginResponseData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GoogleResponse> call, Throwable t) {
                loginResponseData.setValue(null);
            }
        });
        return loginResponseData;
    }

    public LiveData<ResponseBody> forgotPass(ForgotRequest request) {
        MutableLiveData<ResponseBody> forgotResponseData = new MutableLiveData<>();
        apiService.forgotPassword(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    forgotResponseData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                forgotResponseData.setValue(null);
            }
        });
        return forgotResponseData;
    }

    public LiveData<ResponseBody> registerUser(RegisterRequest request) {
        MutableLiveData<ResponseBody> registerResponseData = new MutableLiveData<>();
        apiService.registerUser(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    registerResponseData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                registerResponseData.setValue(null);
            }
        });
        return registerResponseData;
    }
}