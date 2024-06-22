package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.Objects;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityReadBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.MyPreloadModelProvider;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.ReadComicAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ReadViewModel;

public class ReadActivity extends AppCompatActivity {
    private ActivityReadBinding binding;
    ReadViewModel readViewModel;
    private String currentChapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String chapterId = intent.getStringExtra("CHAPTER_ID");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.rcImage.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        if (chapterId != null) {
            currentChapterId = chapterId;
            readViewModel = new ReadViewModel(currentChapterId, this);
            observe();
        }

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void observe() {
        readViewModel.getIsLoaded().observe(this, isLoaded -> {
            if (isLoaded) {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.topNav.setVisibility(View.VISIBLE);
                binding.bottomNav.setVisibility(View.VISIBLE);
                binding.rcImage.setVisibility(View.VISIBLE);
            } else {
                binding.shimmerViewContainer.setVisibility(View.VISIBLE);
                binding.topNav.setVisibility(View.GONE);
                binding.bottomNav.setVisibility(View.GONE);
                binding.rcImage.setVisibility(View.GONE);
            }
        });

        readViewModel.fetchChapterData().observe(this, chapterResponse -> {
            if (chapterResponse != null && chapterResponse.getImages() != null) {
                binding.rcImage.setAdapter(new ReadComicAdapter(chapterResponse.getImages()));
                binding.rcImage.setItemViewCacheSize(20);
                MyPreloadModelProvider myPreloadModelProvider = new MyPreloadModelProvider(this, chapterResponse.getImages());
                ViewPreloadSizeProvider<String> preloadSizeProvider = new ViewPreloadSizeProvider<>();
                RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<>(
                        Glide.with(this), myPreloadModelProvider, preloadSizeProvider, 10);
                binding.rcImage.addOnScrollListener(preloader);
                binding.tvChap.setText("Chapter " + chapterResponse.getChapter().getName());
                Integer nextChap = chapterResponse.getChapter().getNextChap();
                if (nextChap != null) {
                    binding.ivNext.setOnClickListener(v -> {
                        currentChapterId = nextChap.toString();
                        readViewModel = new ReadViewModel(currentChapterId, this);
                        observe();
                    });
                    binding.ivNext.setVisibility(View.VISIBLE);
                } else {
                    binding.ivNext.setVisibility(View.GONE);
                }
                Integer prevChap = chapterResponse.getChapter().getPrevChap();
                if (prevChap != null) {
                    binding.ivPrev.setOnClickListener(v -> {
                        currentChapterId = prevChap.toString();
                        readViewModel = new ReadViewModel(currentChapterId, this);
                        observe();
                    });
                    binding.ivPrev.setVisibility(View.VISIBLE);
                } else {
                    binding.ivPrev.setVisibility(View.GONE);
                }
            }
        });
    }
}