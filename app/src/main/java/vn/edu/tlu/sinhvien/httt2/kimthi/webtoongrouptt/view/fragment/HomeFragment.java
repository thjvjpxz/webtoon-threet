package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentHomeBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.HomeAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.HomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
    String[] tags = {"Action", "Adaptation", "Adult", "ABO", "Adventure"};
    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setLayoutRCV();

        binding.header.tvName.setText("Nghiêm Công Thi");

        addTagsToFlexboxLayout();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        observice();

        return binding.getRoot();
    }


    private void setLayoutRCV() {
        binding.rvBanner.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.rvUpdated.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.rvCompleted.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.rvRanking.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
    }

    private void addTagsToFlexboxLayout() {
        for (String tag : tags) {
            TextView textView = new TextView(new ContextThemeWrapper(getContext(),
                    R.style.TagTextViewStyle));
            textView.setText(tag);

            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 10, 5, 0);

            textView.setLayoutParams(layoutParams);
            binding.flexboxLayout.addView(textView);
        }
    }

    private void observice() {
        homeViewModel.getIsLoaded().observe(getViewLifecycleOwner(), isLoaded -> {
            if (isLoaded) {
                binding.pbBanner.setVisibility(View.GONE);
                binding.pbUpdated.setVisibility(View.GONE);
                binding.pbCompleted.setVisibility(View.GONE);
                binding.pbRanking.setVisibility(View.GONE);
            } else {
                binding.pbBanner.setVisibility(View.VISIBLE);
                binding.pbUpdated.setVisibility(View.VISIBLE);
                binding.pbCompleted.setVisibility(View.VISIBLE);
                binding.pbRanking.setVisibility(View.VISIBLE);
            }

        });

        homeViewModel.getComics().observe(getViewLifecycleOwner(), comics -> {
            if (comics != null) {
                homeAdapter = new HomeAdapter(comics, 1);
                binding.rvBanner.setAdapter(homeAdapter);
                homeAdapter = new HomeAdapter(comics, 2);
                binding.rvUpdated.setAdapter(homeAdapter);
                binding.rvCompleted.setAdapter(homeAdapter);
                homeAdapter = new HomeAdapter(comics, 3);
                binding.rvRanking.setAdapter(homeAdapter);
            }
        });
    }
}