package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityReadStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ReadStoryViewModel;

public class ReadStoryActivity extends AppCompatActivity {
    private ActivityReadStoryBinding binding;
    private ReadStoryViewModel readStoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String slugChapter = intent.getStringExtra("slug_chapter");
        String slugStory = intent.getStringExtra("slug_story");

        readStoryViewModel = new ViewModelProvider(this).get(ReadStoryViewModel.class);
        binding.webView.getSettings().setJavaScriptEnabled(true);

        readStoryViewModel.getReadStoryResponse(slugStory, slugChapter).observe(this,
                readStoryResponse -> {
                    if (readStoryResponse != null) {
                        String content = readStoryResponse.getChapter().getContent();
                        String fullHtml = Utility.getFullHtml(content);
                        binding.webView.loadDataWithBaseURL(null, fullHtml, "text/html", "UTF-8",
                                null);
                    }
                });
    }

    private void observer() {

    }

}