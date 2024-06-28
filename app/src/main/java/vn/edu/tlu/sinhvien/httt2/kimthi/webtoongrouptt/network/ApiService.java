package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.CommentRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.FollowRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ComicByCategoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FilterComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HeartResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.ForgotRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.DetailStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.TwitterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.UpdateRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.GoogleResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ListTypeComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.StoriesByTypeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.StoryHomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadComicResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.TwitterResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.UserResponse;

public interface ApiService {
    @GET("mobile/comics/getComics")
    Call<HomeResponse> getComics();

    @GET("mobile/comics/getDetail/{id}")
    Call<DetailResponse> getDetailComic(@Path("id") String id);

    @GET("mobile/comics/getComicByCategory/{id}")
    Call<ComicByCategoryResponse> getComicByCategory(@Path("id") String id,
                                                     @Query("page") int page);

    @GET("mobile/comics/getListFavourite")
    Call<HeartResponse> getListFavourite();

    @GET("mobile/comics/getChapter/{id}")
    Call<ReadComicResponse> getChapterComic(@Path("id") String id);

    @GET("mobile/comics/getComicsByType/{slug}")
    Call<ListTypeComicResponse> getComicsByType(@Path("slug") String slug, @Query("page") int page);

    @GET("user")
    Call<UserResponse> getUser();

    @POST("user/update")
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

    @GET("comics/getFilterComics")
    Call<FilterComicResponse> getFilterComics(@Query("page") int page,
                                              @Query("sort") int sort,
                                              @Query("status") int status,
                                              @Query("keyword") String keyword);

    // Story
    @GET("mobile/stories/getStories")
    Call<StoryHomeResponse> getStories();

    @GET("mobile/stories/getDetail/{id_story}")
    Call<DetailStoryResponse> getDetailStory(@Path("id_story") int id_story);

    //    @GET("stories/getContentStory/{slug_story}/{slug_chapter}")
//    Call<ReadStoryResponse> getContentStory(@Path("slug_story") String slug_story,
//                                            @Path("slug_chapter") String slug_chapter);
    @GET("mobile/stories/getChapter/{idChapter}")
    Call<ReadStoryResponse> getContentStory(@Path("idChapter") int idChapter);

//    @POST("user/saveHistory")
//    Call<BaseResponse> saveHistory(@Body SaveHistoryRequest saveHistoryRequest);

    @GET("mobile/stories/getListFavoriteStory")
    Call<FavouriteStoryResponse> getListFavoriteStory();

    @GET("mobile/stories/getStoriesByType/{type}")
    Call<StoriesByTypeResponse> getStoriesByType(@Path("type") String type,
                                                 @Query("page") int page);
}
