package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityReadBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.User;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.CommentComicAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.ReadComicAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.CommentViewModel;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ReadViewModel;

public class ReadActivity extends AppCompatActivity implements CommentInterface {
    private ActivityReadBinding binding;
    ReadViewModel readViewModel;
    private String currentChapterId;
    private CommentViewModel commentViewModel;
    private CommentComicAdapter adapterComment;
    private Integer id;
    private Integer chapter_id;
    private User user;

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
            commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
            observe();
        }

        binding.ivBack.setOnClickListener(v -> {
            finish();
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

        binding.ivComment.setOnClickListener(v -> {
            binding.commentsLayout.setVisibility(View.VISIBLE);
        });

        binding.ivClose.setOnClickListener(v -> {
            binding.commentsLayout.setVisibility(View.GONE);
        });

        binding.btnSend.setOnClickListener(v -> {
            comment();
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
                user = chapterResponse.getUser();
                id = chapterResponse.getChapter().getComic_id();
                chapter_id = chapterResponse.getChapter().getId();
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
                adapterComment = new CommentComicAdapter(chapterResponse.getComments(), this);
                binding.rvComments.setLayoutManager(new LinearLayoutManager(this));
                binding.rvComments.setAdapter(adapterComment);
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

    public void comment () {
        EditText editText = binding.edtComment;
        String content = editText.getText().toString();
        if (!content.isEmpty()) {
            commentViewModel.commentComic(id, chapter_id, content).observe(this, isSuccess -> {
                if (isSuccess) {
                    editText.setText("");
                    Comment comment = new Comment();
                    comment.setContent(content);
                    comment.setLike(0);
                    comment.setDislike(0);
                    comment.setUser(user);
                    adapterComment.addComment(comment);
                } else {
                    Toast.makeText(this, "Bình luận thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Vui lòng nhập nội dung bình luận", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void likeComment(String commentId) {
        commentViewModel.likeComment(commentId).observe(this, isSuccess -> {
            if (isSuccess) {
                Comment comment = adapterComment.getCommentById(commentId);
                if (comment != null) {
                    comment.setLike(comment.getLike() + 1);
                    adapterComment.notifyDataSetChanged();
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void dislikeComment(String commentId) {
        commentViewModel.dislikeComment(commentId).observe(this, isSuccess -> {
            if (isSuccess) {
                Comment comment = adapterComment.getCommentById(commentId);
                if (comment != null) {
                    comment.setDislike(comment.getDislike() + 1);
                    adapterComment.notifyDataSetChanged();
                }
            }
        });
    }

    public void reportComment(String commentId) {
        commentViewModel.reportComment(commentId);
    }
}