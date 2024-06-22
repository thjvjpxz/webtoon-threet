package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.FollowRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadComicResponse;

public interface ApiService {
    @GET("mobile/comics/getComics")
    Call<HomeResponse> getComics();

    @GET("mobile/comics/getDetail/{id}")
    Call<DetailResponse> getDetailComic(@Path("id") String id);

    @GET("mobile/comics/getChapter/{id}")
    Call<ReadComicResponse> getChapterComic(@Path("id") String id);

    @POST("auth/register")
    Call<ResponseBody> registerUser(@Body RegisterRequest registerRequest);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("follow")
    Call<BaseResponse> follow(@Body FollowRequest followRequest);
}
