package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;

public class HomeRepository {
    private static HomeRepository instance;
    private ApiService apiService;

    private HomeRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public static HomeRepository getInstance() {
        if (instance == null) {
            synchronized (HomeRepository.class) {
                if (instance == null) {
                    instance = new HomeRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Comic>> getComics(Runnable isLoaded) {
        MutableLiveData<List<Comic>> comics = new MutableLiveData<>();

        apiService.getComics().enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        comics.setValue(response.body().getComicsHot());
                    }
                }
                isLoaded.run();
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                comics.setValue(null);
                isLoaded.run();
            }
        });
        return comics;
    }
}
