package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityDetailStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.TabDetailStoryAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.DetailStoryViewModel;

public class DetailStoryActivity extends AppCompatActivity {

    private ActivityDetailStoryBinding binding;
    private TabDetailStoryAdapter tab;
    private DetailStoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewModel = new ViewModelProvider(this).get(DetailStoryViewModel.class);

        handleTabLayout();
        processFollow();
        processBack();

        Story story = getStoryIntent();

        processTab(story);

        observer(story.getId());

        if (story == null) {
            Toast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            return;
        }

        fillData(story);
    }

    private void observer(int storyId) {
        viewModel.getDetailStoryResponse(storyId).observe(this, response -> {
            if (response != null) {
                binding.ivFollow.setSelected(response.isFollow());
            }
        });
    }

    private void processTab(Story story) {
        tab = new TabDetailStoryAdapter(getSupportFragmentManager(), getLifecycle(), story);
        binding.viewPager2.setAdapter(tab);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null) {
                    binding.viewPager2.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }

    private void handleTabLayout() {
        binding.tabLayout.removeAllTabs();
        binding.tabLayout.setVisibility(View.VISIBLE);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Giới thiệu"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Chương truyện"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Bình luận"));
    }


    private Story getStoryIntent() {
        Intent intent = getIntent();
        Story story = intent.getSerializableExtra("story") != null ?
                (Story) intent.getSerializableExtra("story") : null;
        return story;
    }

    private void fillData(Story story) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));

        Glide.with(this).load(story.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.ALL).apply(requestOptions).into(binding.imgThumbnail);
        Glide.with(this).load(story.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.imgThumbnailLarge);
        binding.tvNameStory.setText(story.getName());
    }

    private void processFollow() {
        binding.ivFollow.setOnClickListener(v -> {
            
            boolean isSeleted = !binding.ivFollow.isSelected();
            binding.ivFollow.setSelected(isSeleted);

            ObjectAnimator scaleXUp = ObjectAnimator.ofFloat(binding.ivFollow, "scaleX", 1.2f);
            ObjectAnimator scaleYUp = ObjectAnimator.ofFloat(binding.ivFollow, "scaleY", 1.2f);
            ObjectAnimator scaleXDown = ObjectAnimator.ofFloat(binding.ivFollow, "scaleX", 1.0f);
            ObjectAnimator scaleYDown = ObjectAnimator.ofFloat(binding.ivFollow, "scaleY", 1.0f);

            AnimatorSet scaleUp = new AnimatorSet();
            scaleUp.playTogether(scaleXUp, scaleYUp);
            scaleUp.setDuration(150); // Thời gian phóng to

            AnimatorSet scaleDown = new AnimatorSet();
            scaleDown.playTogether(scaleXDown, scaleYDown);
            scaleDown.setDuration(150); // Thời gian thu nhỏ lại

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(scaleUp, scaleDown);
            animatorSet.start();
        });
    }

    private void processBack() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}