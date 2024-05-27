package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;

public interface ApiService {
    @GET("comics/getComics")
    Call<HomeResponse> getComics();
}
