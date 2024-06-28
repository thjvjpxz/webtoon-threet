package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentFavAndHisBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.FavouriteAndHistoryTabAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.FavouriteStoryViewModel;

public class FavAndHisFragment extends Fragment {
    private FragmentFavAndHisBinding binding;
    private FavouriteAndHistoryTabAdapter tabAdapter;
    private FavouriteStoryViewModel viewModel;

    public FavAndHisFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        observer();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavAndHisBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(FavouriteStoryViewModel.class);

        observer();

        return binding.getRoot();
    }

    private void observer() {
        viewModel.getFavouriteStory().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                handleTabLayout(data.getFollows(), data.getHistories());
            }
        });
    }

    private void handleTabLayout(List<FavouriteStoryResponse.Follow> follows, List<FavouriteStoryResponse.History> histories) {
        tabAdapter = new FavouriteAndHistoryTabAdapter(this, histories, follows);
        binding.vpFavAndHis.setAdapter(tabAdapter);
        new TabLayoutMediator(binding.tlFavOrHis, binding.vpFavAndHis,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Yêu thích");
                    } else {
                        tab.setText("Đang đọc");
                    }
                }).attach();
    }
}