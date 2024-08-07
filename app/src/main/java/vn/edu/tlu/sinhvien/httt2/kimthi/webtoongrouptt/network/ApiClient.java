package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network;

import android.content.Context;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;

public class ApiClient {
    private static final String BASE_URL = "https://db.truyenhdc.com/api/";
    private static Retrofit retrofit;
    private static Retrofit retrofitHeader;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static Retrofit getRetrofitHeader(Context context) {
        if (retrofitHeader == null) {
            synchronized (ApiClient.class) {
                if (retrofitHeader == null) {
                    Interceptor interceptor = chain -> {
                        SharedPrefManager sharedPrefManager =
                                SharedPrefManager.getInstance(context);
                        String fullToken = "Bearer " + sharedPrefManager.getToken();
                        Request request = chain.request();
                        Request.Builder newRequest = request.newBuilder()
                                .removeHeader("Authorization")
                                .addHeader("Authorization", fullToken);
                        return chain.proceed(newRequest.build());
                    };


                    OkHttpClient.Builder client =
                            new OkHttpClient.Builder().addInterceptor(interceptor);
                    retrofitHeader = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client.build())
                            .build();
                }
            }
        }

        return retrofitHeader;
    }

    public static void setRetrofitHeaderNull() {
        retrofitHeader = null;
    }
}
