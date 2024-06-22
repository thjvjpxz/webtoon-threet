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

import java.util.Objects;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityReadBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.ReadComicAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ReadViewModel;

public class ReadActivity extends AppCompatActivity {
    private ActivityReadBinding binding;
    ReadViewModel readViewModel;

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
            readViewModel = new ReadViewModel(chapterId, this);
            observe();
        }
    }

    private void observe() {
        readViewModel.fetchChapterData().observe(this, chapterResponse -> {
            if (chapterResponse != null) {
                binding.rcImage.setAdapter(new ReadComicAdapter(chapterResponse.getImages()));
            }
        });
    }
}