package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityDetailStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.LayoutCommentStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.CustomToast;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.TabDetailStoryAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.CmtStoryListFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.CommentViewModel;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.DetailStoryViewModel;

public class DetailStoryActivity extends AppCompatActivity {

    private ActivityDetailStoryBinding binding;
    private LayoutCommentStoryBinding layoutCommentStoryBinding;
    private TabDetailStoryAdapter tab;
    private DetailStoryViewModel viewModel;
    private CommentViewModel cmtViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewModel = new ViewModelProvider(this).get(DetailStoryViewModel.class);
        cmtViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        handleTabLayout();
        processBack();

        Story story = getStoryIntent();

        processTab(story);
        processFollow(story);

        observer(story.getId());

        if (story == null) {
            CustomToast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT, CustomToast.ERROR).show();
            return;
        }

        fillData(story);
        processAddComment(story);
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
                    if (tab.getPosition() == 2) {
                        binding.fabAddComment.setVisibility(View.VISIBLE);
                    } else {
                        binding.fabAddComment.setVisibility(View.GONE);
                    }
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

    private void processFollow(Story story) {
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

            processCallFollow(String.valueOf(story.getId()));
        });
    }

    private void processCallFollow(String storyId) {
        viewModel.followStory(storyId).observe(this, response -> {
            if (response != null) {
                CustomToast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT,
                        CustomToast.SUCCESS).show();
            }
        });
    }

    private void processBack() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void processAddComment(Story story) {
        binding.fabAddComment.setOnClickListener(v -> {
            layoutCommentStoryBinding = LayoutCommentStoryBinding.inflate(getLayoutInflater());
            showBottomSheetDialog(story);

        });
    }

    private void showBottomSheetDialog(Story story) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(layoutCommentStoryBinding.getRoot());
        bottomSheetDialog.show();
        binding.fabAddComment.setVisibility(View.GONE);
        CmtStoryListFragment fragment = (CmtStoryListFragment) getFragmentComment(2);
        if (fragment != null) {
            fragment.onPause();
        }
        layoutCommentStoryBinding.tvSendComment.setOnClickListener(v -> {
            String comment = layoutCommentStoryBinding.edtCommentStory.getText().toString();
            if (comment.isEmpty()) {
                CustomToast.makeText(this, "Vui lòng nhập bình luận!", Toast.LENGTH_SHORT,
                        CustomToast.WARNING).show();
                return;
            }
            processSendComment(comment, story);
            bottomSheetDialog.dismiss();
        });


        bottomSheetDialog.setOnDismissListener(dialog -> {
            binding.fabAddComment.setVisibility(View.VISIBLE);
        });
    }

    private Fragment getFragmentComment(int position) {
        Fragment fragment = getFragmentAtPosition(position);
        if (fragment instanceof CmtStoryListFragment) {
            return fragment;
        }
        return null;
    }

    private Fragment getFragmentAtPosition(int position) {
        return tab.getRegisteredFragment(position);
    }

    private void processSendComment(String comment, Story story) {
        cmtViewModel.commentStory(story.getId(), null, comment).observe(this, isSuccess -> {
            if (isSuccess) {
                CmtStoryListFragment fragment = (CmtStoryListFragment) getFragmentComment(2);
                if (fragment != null) {
                    fragment.onResume();
                }
            }
        });
    }
}