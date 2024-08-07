package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.CommentRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.BaseResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.CustomToast;

public class CommentRepository {
    private static CommentRepository instance;
    ApiService apiService;
    Context context;
    private CommentRepository(Context context) {
        this.context = context.getApplicationContext();
        apiService = ApiClient.getRetrofitHeader(this.context).create(ApiService.class);
    }

    public static synchronized CommentRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CommentRepository(context);
        }
        return instance;
    }

    public LiveData<Boolean> comment(Integer comicId, Integer storyId, Integer chapterId, String content, String type) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        CommentRequest commentRequest = new CommentRequest(content, type, chapterId, comicId, storyId);
        Call<BaseResponse> call = apiService.comment(commentRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if(baseResponse != null) {
                        CustomToast.makeText(context, baseResponse.getMessage(), Toast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                        result.setValue(true);
                    }
                } else {
                    CustomToast.makeText(context, "Bình luận thất bại", Toast.LENGTH_SHORT, CustomToast.ERROR).show();
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                CustomToast.makeText(context, "Bình luận thất bại", Toast.LENGTH_SHORT, CustomToast.ERROR).show();
                result.setValue(false);
            }
        });
        return result;
    }
    public LiveData<Boolean> likeComment(String commentId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        apiService.likeComment(commentId).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        BaseResponse baseResponse = response.body();
                        if(Objects.equals(baseResponse.getStatus(), "success")){
                            result.setValue(true);
                        }else{
                            result.setValue(false);
                        }
                        Toast.makeText(context, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Thích bình luận thất bại", Toast.LENGTH_SHORT).show();
                result.setValue(false);
            }
        });
        return result;
    }

    public LiveData<Boolean> dislikeComment(String commentId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        apiService.dislikeComment(commentId).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        BaseResponse baseResponse = response.body();
                        if(Objects.equals(baseResponse.getStatus(), "success")){
                            result.setValue(true);
                        }else{
                            result.setValue(false);
                        }
                        Toast.makeText(context, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Không thích bình luận thất bại", Toast.LENGTH_SHORT).show();
                result.setValue(false);
            }
        });
        return result;
    }

    public LiveData<Boolean> reportComment(String commentId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        apiService.reportComment(commentId).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        BaseResponse baseResponse = response.body();
                        if(Objects.equals(baseResponse.getStatus(), "success")){
                            result.setValue(true);
                        }else{
                            result.setValue(false);
                        }
                        Toast.makeText(context, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Báo cáo luận thất bại", Toast.LENGTH_SHORT).show();
                result.setValue(false);
            }
        });
        return result;
    }
}
