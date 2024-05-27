package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.RegisterRequest;


public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://db.truyenhdc.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

        @GET("comics/getComics")
    Call<HomeResponse> getComics();
        @POST("auth/register")
        Call<ResponseBody> registerUser(@Body RegisterRequest registerRequest);

        @POST("auth/login")
        Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}