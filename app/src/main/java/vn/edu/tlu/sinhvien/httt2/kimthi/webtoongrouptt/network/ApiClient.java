package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;

public class ApiClient {
    private static final String BASE_URL = "https://db.truyenhdc.com/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(context);
                    String token = sharedPrefManager.getToken();
                    Interceptor intercepter = chain -> {
                        Request request = chain.request();
                        Request.Builder newRequest = request.newBuilder()
                                .addHeader("Authorization", "Bearer " + token);

                        return chain.proceed(newRequest.build());
                    };

                    OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(intercepter);

                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client.build())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
