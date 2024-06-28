package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityStoriesByTypeBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.TypeStoryConst;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.TabTypeStoryAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.StoryHomeViewModel;

public class StoriesByTypeActivity extends AppCompatActivity {
    private ActivityStoriesByTypeBinding binding;
    private TabTypeStoryAdapter tabAdapter;
    private StoryHomeViewModel viewModel;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoriesByTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        type = getIntentData();

        viewModel = new ViewModelProvider(this).get(StoryHomeViewModel.class);

        if (type == -1) {
            Toast.makeText(this, "Không tìm thấy truyện", Toast.LENGTH_SHORT).show();
            finish();
        }

        handleBack();

        setupTabLayoutAndViewPager();
    }

    private void setupTabLayoutAndViewPager() {
        tabAdapter = new TabTypeStoryAdapter(getSupportFragmentManager(), getLifecycle());
        binding.vpStoriesByType.setAdapter(tabAdapter);

//        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(binding.tlStoriesByType, binding.vpStoriesByType, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Truyện hot");
                    break;
                case 1:
                    tab.setText("Truyện đã hoàn thành");
                    break;
                case 2:
                    tab.setText("Truyện convert");
                    break;
                case 3:
                    tab.setText("Truyện dịch");
                    break;
            }
        }).attach();

        // Chọn tab ban đầu dựa trên loại truyện từ Intent
        if (type != -1 && type < binding.tlStoriesByType.getTabCount()) {
            TabLayout.Tab initialTab = binding.tlStoriesByType.getTabAt(type);
            if (initialTab != null) {
                initialTab.select();
            }
            binding.vpStoriesByType.setCurrentItem(type, false);
        }
    }

    private int getIntentData() {
        return getIntent().getIntExtra("type", -1);
    }

    private void handleBack() {
        binding.btnBack.setOnClickListener(v -> finish());
    }
}