package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.adapter.HomeAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.api.ApiService;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.api.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;

public class HomeFragment extends Fragment {
    RecyclerView rvBanner, rvUpdated, rvCompleted, rvRanking;
    View headerView;
    TextView tvName;
    String[] tags = {"Action", "Adaptation", "Adult", "ABO", "Adventure"};
    FlexboxLayout flexboxLayout;
    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView =  inflater.inflate(R.layout.fragment_home, container, false);
        rvBanner = homeView.findViewById(R.id.rvBanner);
        rvBanner.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvUpdated = homeView.findViewById(R.id.rvUpdated);
        rvUpdated.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvCompleted = homeView.findViewById(R.id.rvCompleted);
        rvCompleted.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvRanking = homeView.findViewById(R.id.rvRanking);
        rvRanking.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        flexboxLayout = homeView.findViewById(R.id.flexboxLayout);
        for (String tag : tags) {
            TextView textView = new TextView(new ContextThemeWrapper(getContext(), R.style.TagTextViewStyle));
            textView.setText(tag);
            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 10, 5, 0);
            textView.setLayoutParams(layoutParams);
            flexboxLayout.addView(textView);
        }
        View headerView = homeView.findViewById(R.id.header);
        tvName = headerView.findViewById(R.id.tvName);
        tvName.setText("Nghiêm Quang Thắng");
        ApiService.apiService.getComics().enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeResponse> call, @NonNull Response<HomeResponse> response) {
                if (response.isSuccessful()) {
                    HomeResponse homeResponses = response.body();
                    if (homeResponses != null) {
                        List<Comic> comicsHot = homeResponses.getComicsHot();
                        if (comicsHot != null) {
                            HomeAdapter bannerAdapter = new HomeAdapter(comicsHot, 1);
                            HomeAdapter updatedAdapter = new HomeAdapter(comicsHot, 2);
                            rvBanner.setAdapter(bannerAdapter);
                            rvUpdated.setAdapter(updatedAdapter);
                            rvCompleted.setAdapter(updatedAdapter);
                            rvRanking.setAdapter(new HomeAdapter(comicsHot, 3));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeResponse> call, @NonNull Throwable t) {
                Log.e("HomeFragment", "onFailure: " + t.getMessage());
            }
        });


        return homeView;
    }
}