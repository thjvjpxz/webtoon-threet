package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityReadBinding;
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
        String comicId = intent.getStringExtra("COMIC_ID");
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

        binding.ivHome.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        });

        binding.rcImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastScrollPosition = 0;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (binding.topNav.getVisibility() == View.VISIBLE) {
                        binding.topNav.setVisibility(View.GONE);
                    }
                } else if (dy < 0) {
                    if (binding.topNav.getVisibility() == View.GONE) {
                        binding.topNav.setVisibility(View.VISIBLE);
                    }
                }
                lastScrollPosition = dy;
            }
        });
        binding.tvFollow.setOnClickListener(v -> {
            readViewModel.followComic(comicId, "comic").observe(this, this::changeIconFollow);
        });
    }

    private void observe() {
        readViewModel.getIsLoaded().observe(this, isLoaded -> {
            if (isLoaded) {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.topNav.setVisibility(View.VISIBLE);
                binding.rcImage.setVisibility(View.VISIBLE);
            } else {
                binding.shimmerViewContainer.setVisibility(View.VISIBLE);
                binding.topNav.setVisibility(View.GONE);
                binding.rcImage.setVisibility(View.GONE);
            }
        });

        readViewModel.fetchChapterData().observe(this, chapterResponse -> {
            if (chapterResponse != null && chapterResponse.getImages() != null) {
                binding.rcImage.setAdapter(new ReadComicAdapter(chapterResponse.getImages()));
                binding.rcImage.setItemViewCacheSize(20);
                binding.rcImage.setHasFixedSize(true);
                binding.tvChap.setText("Chapter " + chapterResponse.getChapter().getName());
                Integer nextChap = chapterResponse.getChapter().getNextChap();
                if (nextChap != null) {
                    binding.ivNext.setOnClickListener(v -> {
                        currentChapterId = nextChap.toString();
                        readViewModel = new ReadViewModel(currentChapterId, this);
                        observe();
                    });
                    binding.ivNext.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary_color));
                } else {
                    binding.ivNext.setBackgroundTintList(null);
                }
                Boolean isFollow = chapterResponse.getFollow();
                changeIconFollow(isFollow);
                Integer prevChap = chapterResponse.getChapter().getPrevChap();
                if (prevChap != null) {
                    binding.ivPrevious.setOnClickListener(v -> {
                        currentChapterId = prevChap.toString();
                        readViewModel = new ReadViewModel(currentChapterId, this);
                        observe();
                    });
                    binding.ivPrevious.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary_color));
                } else {
                    binding.ivPrevious.setBackgroundTintList(null);
                }
            }
        });
    }

    void changeIconFollow(Boolean isFollow) {
        if (isFollow) {
            binding.tvFollow.setText("Hủy theo dõi");
            binding.tvFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_svgrepo_com, 0, 0, 0);
        } else {
            binding.tvFollow.setText("Theo dõi");
            binding.tvFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
        }
    }
}