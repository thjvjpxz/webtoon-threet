package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityCommentBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.CommentAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.UserViewModel;

public class CommentActivity extends AppCompatActivity {
    ActivityCommentBinding binding;
    UserViewModel userViewModel;
    CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        observeData();
        processBack();
    }

    public void observeData() {
        userViewModel.fetchUserData().observe(this, data -> {
            if (data != null) {
                commentAdapter = new CommentAdapter(data.getComments(), data.getUser());
                binding.recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
                binding.recyclerViewComment.setAdapter(commentAdapter);
            } else {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processBack () {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }
}