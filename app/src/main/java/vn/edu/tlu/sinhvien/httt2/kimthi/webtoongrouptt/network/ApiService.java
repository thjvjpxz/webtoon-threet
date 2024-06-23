package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.ForgotRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.GoogleResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.StoryHomeResponse;

public interface ApiService {
    @GET("mobile/comics/getComics")
    Call<HomeResponse> getComics();

    @POST("auth/register")
    Call<ResponseBody> registerUser(@Body RegisterRequest registerRequest);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("auth/forgot")
    Call<ResponseBody> forgotPassword(@Body ForgotRequest forgotRequest);

    @POST("auth/google")
    Call<GoogleResponse> loginGoogle(@Body GoogleRequest googleRequest);


    // Story
    @GET("mobile/stories/getStories")
    Call<StoryHomeResponse> getStories();
}
