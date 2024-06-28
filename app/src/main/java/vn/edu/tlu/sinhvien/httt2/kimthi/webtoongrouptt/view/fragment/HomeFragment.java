package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import android.Manifest;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentHomeBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Category;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.CategoryActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.ListTypeComicActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.HomeAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ActivityResultLauncher<String> requestPermissionLauncher;

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

        handleClickBtnStory();

        handleClickMore();

        setLayoutRCV();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        binding.header.tvName.setText(sharedPrefManager.getName());
        String avatarUrl = sharedPrefManager.getAvatar();
        Glide.with(this).load(avatarUrl).into(binding.header.imgAvatar);

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

        homeViewModel.fetchHomeData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                binding.rvBanner.setAdapter(new HomeAdapter((MainActivity) getActivity(),
                        data.getPopularComics(), 1));
                binding.rvUpdated.setAdapter(new HomeAdapter((MainActivity) getActivity(),
                        data.getRecentComics(), 2));
                binding.rvCompleted.setAdapter(new HomeAdapter((MainActivity) getActivity(),
                        data.getCompletedComics(), 2));
                binding.rvRanking.setAdapter(new HomeAdapter((MainActivity) getActivity(),
                        data.getRankingComics(), 3));
                for (Category tag : data.getCategories()) {
                    TextView textView = new TextView(new ContextThemeWrapper(getContext(),
                            R.style.TagTextViewStyle));
                    String text = tag.getName();
                    String capitalizedText = Utility.capitalizeFirstLetter(text);
                    textView.setText(capitalizedText);
                    FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 10, 5, 0);

                    textView.setLayoutParams(layoutParams);
                    binding.flexboxLayout.addView(textView);
                    textView.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), CategoryActivity.class);
                        intent.putExtra("categoryId", String.valueOf(tag.getId()));
                        intent.putExtra("page", 1);
                        startActivity(intent);
                    });
                }
            } else {
                Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                Log.d("HomeFragment", "Không có dữ liệu");
            }
        });
    }

    private void handleClickBtnStory() {
        binding.header.btnStory.setText("Truyện chữ");
        binding.header.btnStory.setOnClickListener(v -> {
            SharedPrefManager share = SharedPrefManager.getInstance(getContext());
            share.saveTypeWebtoon(Constants.TYPE_WEBTOON_STORY);
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);

            getActivity().finish();
        });
    }

    private void handleClickMore(){
        binding.tvMoreRecent.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListTypeComicActivity.class);
            intent.putExtra("slug", "truyen-moi-cap-nhat");
            startActivity(intent);
        });

        binding.tvMoreCompleted.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListTypeComicActivity.class);
            intent.putExtra("slug", "truyen-hoan-thanh");
            startActivity(intent);
        });
    }
}
