package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityDetailBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Author;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Category;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.User;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.CommentRepository;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.CommentComicAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.DetailAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.CommentViewModel;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.DetailViewModel;

public class DetailActivity extends AppCompatActivity implements CommentInterface {
    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;
    private Integer id;
    private User user;
    private CommentComicAdapter adapterComment;
    private CommentViewModel commentViewModel;

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
            id = Integer.parseInt(comicId);
            detailViewModel = new DetailViewModel(comicId, this);
            commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
            observice();
        }
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.ivShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Webtoon Group TT");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://truyenhdc.com");
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ ứng dụng"));
        });

        binding.ivFollow.setOnClickListener(v -> {
            detailViewModel.followComic(comicId, "comic");
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
            user = data.getUser();
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
            DetailAdapter adapter = new DetailAdapter(data.getChapters(), data.getHistory(), this);
            binding.listChapter.setAdapter(adapter);
            adapterComment = new CommentComicAdapter(data.getComments(), this);
            binding.rvComments.setLayoutManager(new LinearLayoutManager(this));
            binding.rvComments.setAdapter(adapterComment);
        });
    }

    public void openReadActivity(String id) {
        Intent intent = new Intent(this, ReadActivity.class);
        intent.putExtra("CHAPTER_ID", id);
        intent.putExtra("COMIC_ID", getIntent().getStringExtra("COMIC_ID"));
        startActivity(intent);
    }

    public void comment () {
        EditText editText = binding.edtComment;
        String content = editText.getText().toString();
        if (!content.isEmpty()) {
            commentViewModel.commentComic(id, null, content).observe(this, isSuccess -> {
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