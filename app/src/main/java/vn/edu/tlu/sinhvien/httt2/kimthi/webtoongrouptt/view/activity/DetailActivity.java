package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityDetailBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Author;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Category;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.DetailAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.DetailViewModel;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        String comicId = intent.getStringExtra("COMIC_ID");
        setContentView(binding.getRoot());
        binding.listChapter.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        if (comicId != null) {
            detailViewModel = new DetailViewModel(comicId, this);
            observice();
        }
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.ivShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Webtoon Group TT");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "truyenhdc.com");
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ ứng dụng"));
        });

        binding.ivFollow.setOnClickListener(v -> {
            detailViewModel.followComic(comicId, "comic");
        });
    }

    private void observice() {
        detailViewModel.getIsLoaded().observe(this, isLoaded -> {
            if (isLoaded) {
                binding.shimmerThumbnail.setVisibility(View.GONE);
                binding.shimmerTitle.setVisibility(View.GONE);
                binding.shimmerOriginName.setVisibility(View.GONE);
                binding.shimmerAuthor.setVisibility(View.GONE);
                binding.shimmerStatus.setVisibility(View.GONE);
            } else {
                binding.shimmerThumbnail.setVisibility(View.VISIBLE);
                binding.shimmerTitle.setVisibility(View.VISIBLE);
                binding.shimmerOriginName.setVisibility(View.VISIBLE);
                binding.shimmerAuthor.setVisibility(View.VISIBLE);
                binding.shimmerStatus.setVisibility(View.VISIBLE);
            }
        });
        detailViewModel.fetchDetailData().observe(this, data -> {
            binding.title.setText(data.getComic().getName());
            binding.tvRate.setText(String.valueOf(data.getComic().getRating()));
            binding.tvViews.setText(String.valueOf(data.getComic().getTotal_views()));
            String originName = data.getComic().getOrigin_name();
            if (originName == null) {
                originName = "Đang cập nhật";
            }
            binding.tvOriginName.setText(originName);
            List<Author> authors = data.getComic().getAuthors();
            if (authors != null && authors.size() > 1) {
                String firstAuthorName = authors.get(0).getName();
                binding.tvAuthor.setText(firstAuthorName);
            }else{
                binding.tvAuthor.setText("Đang cập nhật");
            }
            binding.tvStatus.setText(data.getComic().getStatus());
            String thumbnail = data.getComic().getThumbnail();
            Glide.with(this).load(thumbnail).into(binding.imageView);
            for (Category tag: data.getComic().getCategories()){
                TextView textView = new TextView(new ContextThemeWrapper(this, R.style.TagTextViewStyle));
                textView.setText(tag.getName());
                FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 10, 5, 0);
                textView.setLayoutParams(layoutParams);
                binding.flexboxCategory.addView(textView);
            }
            binding.listChapter.setAdapter(new DetailAdapter(data.getChapters(), this));
        });
    }

    public void openReadActivity(String id) {
        Intent intent = new Intent(this, ReadActivity.class);
        intent.putExtra("CHAPTER_ID", id);
        startActivity(intent);
    }
}