package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.CommentRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.FollowRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ComicByCategoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HeartResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.ForgotRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.TwitterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.UpdateRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.GoogleResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.TwitterResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.UserResponse;

public interface ApiService {
    @GET("mobile/comics/getComics")
    Call<HomeResponse> getComics();

    @GET("mobile/comics/getDetail/{id}")
    Call<DetailResponse> getDetailComic(@Path("id") String id);

    @GET("mobile/comics/getComicByCategory/{id}")
    Call<ComicByCategoryResponse> getComicByCategory(@Path("id") String id, @Query("page") int page);

    @GET("mobile/comics/getListFavourite")
    Call<HeartResponse> getListFavourite();

    @GET("mobile/comics/getChapter/{id}")
    Call<ReadComicResponse> getChapterComic(@Path("id") String id);
    @GET("user")
    Call<UserResponse> getUser();

    @POST("/user/update")
    Call<LoginResponse> updateUser(@Body UpdateRequest updateRequest);

    @POST("logout")
    Call<ResponseBody> logOut();

    @POST("auth/register")
    Call<ResponseBody> registerUser(@Body RegisterRequest registerRequest);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("follow")
    Call<BaseResponse> follow(@Body FollowRequest followRequest);

    @POST("comment")
    Call<BaseResponse> comment(@Body CommentRequest commentRequest);

    @POST("comment/like/{id}")
    Call<BaseResponse> likeComment(@Path("id") String id);

    @POST("comment/dislike/{id}")
    Call<BaseResponse> dislikeComment(@Path("id") String id);

    @POST("comment/report/{id}")
    Call<BaseResponse> reportComment(@Path("id") String id);
    @POST("auth/forgot")
    Call<ResponseBody> forgotPassword(@Body ForgotRequest forgotRequest);

    @POST("auth/google")
    Call<GoogleResponse> loginGoogle(@Body GoogleRequest googleRequest);

    @POST("auth/twitter")
    Call<TwitterResponse> loginTwitter(@Body TwitterRequest twitterRequest);
}
