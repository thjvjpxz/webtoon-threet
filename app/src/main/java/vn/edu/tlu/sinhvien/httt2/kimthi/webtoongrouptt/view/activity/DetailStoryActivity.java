package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.text.HtmlCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityDetailStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Author;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StoryChapterAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.DetailStoryViewModel;

public class DetailStoryActivity extends AppCompatActivity {

    private ActivityDetailStoryBinding binding;
    private DetailStoryViewModel viewModel;
    private StoryChapterAdapter storyChapterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        processFollow();
        processBack();
        processShowMore();

        Story story = getStoryIntent();

        if (story == null) {
            Toast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel = new ViewModelProvider(this).get(DetailStoryViewModel.class);
        storyChapterAdapter = new StoryChapterAdapter(new ArrayList<>());
        binding.rvChapters.setLayoutManager(new LinearLayoutManager(this));

        fillData(story);
        observer(story);

        binding.rvChapters.setAdapter(storyChapterAdapter);
    }

    private void observer(Story story) {
        viewModel.getDetailStoryResponse(story.getId()).observe(this, detailStoryResponse -> {
            if (detailStoryResponse != null) {
                storyChapterAdapter.setChapters(detailStoryResponse.getChapters());
            }
        });
    }

    private Story getStoryIntent() {
        Intent intent = getIntent();
        Story story = intent.getSerializableExtra("story") != null ?
                (Story) intent.getSerializableExtra("story") : null;
        return story;
    }

    private void fillData(Story story) {
        binding.tvNameStory2.setText(story.getOrigin_name() == null ? "Không có" :
                story.getOrigin_name());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));

        Glide.with(this).load(story.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions)
                .into(binding.imgThumbnail);
        Glide.with(this).load(story.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgThumbnailLarge);
        binding.tvNameStory.setText(story.getName());
        binding.tvRate.setText(String.valueOf(story.getRating()));
        binding.tvView.setText(String.valueOf(story.getTotal_views()));
        String authors = "";
        for (int i = 0; i < story.getAuthors().size(); i++) {
            String author = story.getAuthors().get(i).getName();
            author = Utility.capitalizeFirstLetter(author);
            authors += author;
            if (i < story.getAuthors().size() - 1) {
                authors += ", ";
            }
        }
        binding.tvAuthor.setText(authors);
        binding.tvStatus.setText(story.getStatus());

        String categories = "";
        for (int i = 0; i < story.getCategories().size(); i++) {
            String category = story.getCategories().get(i).getName();
            category = Utility.capitalizeFirstLetter(category);
            categories += category;
            if (i < story.getCategories().size() - 1) {
                categories += ", ";
            }
        }
        binding.tvCategories.setText(categories);

        String content = story.getContent() != null ? String.valueOf(HtmlCompat.fromHtml(story.getContent(),
                HtmlCompat.FROM_HTML_MODE_COMPACT)) : "Truyện rất hay, vui lòng đọc để biết thêm chi tiết!";
        binding.tvIntro.setText(content);
    }

    private void processShowMore() {
        binding.btnShowMore.setOnClickListener(v -> {
            boolean isSeleted = !binding.btnShowMore.isSelected();
            binding.btnShowMore.setSelected(isSeleted);
            if (isSeleted) {
                binding.tvIntro.setMaxLines(Integer.MAX_VALUE);
                binding.tvIntro.setEllipsize(null);
                binding.btnShowMore.setImageResource(R.drawable.baseline_arrow_drop_up_24);
            } else {
                binding.tvIntro.setMaxLines(3);
                binding.tvIntro.setEllipsize(TextUtils.TruncateAt.END);
                binding.btnShowMore.setImageResource(R.drawable.baseline_arrow_drop_down_24);
            }
        });
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