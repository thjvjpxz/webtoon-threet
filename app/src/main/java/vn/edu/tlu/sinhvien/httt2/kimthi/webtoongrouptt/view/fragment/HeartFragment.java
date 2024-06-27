package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentHeartBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Follow;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.History;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.HeartAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.HeartViewModel;

public class HeartFragment extends Fragment {

    FragmentHeartBinding binding;
    HeartViewModel viewModel;
    List<Follow> listFollow;
    List<History> listHistory;

    public HeartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHeartBinding.inflate(inflater, container, false);
        viewModel = new HeartViewModel();
        observeData();
        return binding.getRoot();
    }

    private void observeData() {
        viewModel.fetchData().observe(getViewLifecycleOwner(), data -> {
            listFollow = data.getFollows();
            listHistory = data.getHistories();

            initTabLayout();
        });
    }

    private void initTabLayout() {
        TabLayout tabLayout = binding.tabLayout;
        ViewPager2 viewPager = binding.viewPager;
        HeartAdapter adapter = new HeartAdapter(this, listFollow, listHistory);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Theo dõi");
                    break;
                case 1:
                    tab.setText("Lịch sử");
                    break;
            }
        }).attach();
    }
}