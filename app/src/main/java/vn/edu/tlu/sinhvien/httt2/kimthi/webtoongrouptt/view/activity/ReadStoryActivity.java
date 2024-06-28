package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityReadStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.LayoutCommentStoryBottomSheetBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.PopupLayoutBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ReadStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Comment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.CustomToast;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StoryCmtAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.CommentViewModel;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ReadStoryViewModel;

public class ReadStoryActivity extends AppCompatActivity {
    private ActivityReadStoryBinding binding;
    private ReadStoryViewModel readStoryViewModel;
    private PopupLayoutBinding popupView;
    private CommentViewModel commentViewModel;
    private StoryCmtAdapter adapter;
    private int storyId;
    private List<Comment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        int idChapter = intent.getIntExtra("idChapter", -1);

        if (idChapter == -1) {
            CustomToast.makeText(this, "Không tìm thấy chương truyện", Toast.LENGTH_SHORT,
                    CustomToast.ERROR).show();
            finish();
        }

        readStoryViewModel = new ViewModelProvider(this).get(ReadStoryViewModel.class);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);


        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                applyStoredColors();
            }
        });

        processBtnBack();

        popupView = PopupLayoutBinding.inflate(LayoutInflater.from(this));

        // click text color
        for (int i = 0; i < popupView.glTextColor.getChildCount(); i++) {
            View child = popupView.glTextColor.getChildAt(i);
            child.setOnClickListener(v -> {
                handleClickTextColor(v);
            });
        }

        // click background color
        for (int i = 0; i < popupView.glBgColor.getChildCount(); i++) {
            View child = popupView.glBgColor.getChildAt(i);
            child.setOnClickListener(v -> {
                handleClickBgColor(v);
            });
        }

        observer(idChapter);
        adapter = new StoryCmtAdapter(comments);
        processBtnFormat();
        handleIvComment(idChapter);
    }

    private void handleIvComment(int chapterId) {
        binding.ivComment.setOnClickListener(v -> {
            View viewDialog =
                    getLayoutInflater().inflate(R.layout.layout_comment_story_bottom_sheet, null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(viewDialog);
            bottomSheetDialog.show();

            LayoutCommentStoryBottomSheetBinding bindingBottomSheet =
                    LayoutCommentStoryBottomSheetBinding.bind(viewDialog);
            bindingBottomSheet.rvCommentStory.setLayoutManager(new LinearLayoutManager(this));

            fetchComment(bindingBottomSheet);
            processSendComment(bindingBottomSheet, storyId, chapterId);
        });
    }

    private void fetchComment(LayoutCommentStoryBottomSheetBinding binding1) {
        if (comments.size() == 0) {
            binding1.tvNoComment.setVisibility(View.VISIBLE);
        } else {
            adapter.setComments(comments);
            binding1.tvNoComment.setVisibility(View.GONE);
            binding1.rvCommentStory.setAdapter(adapter);
        }
    }

    private void processSendComment(LayoutCommentStoryBottomSheetBinding binding1, int storyId,
                                    int chapterId) {
        binding1.btnSendComment.setOnClickListener(v -> {
            if (binding1.edtCommentStory.getText().toString().isEmpty()) {
                CustomToast.makeText(this, "Vui lòng nhập nội dung bình luận", Toast.LENGTH_SHORT,
                        CustomToast.WARNING).show();
                return;
            }
            String content = binding1.edtCommentStory.getText().toString();
            commentViewModel.commentStory(storyId, chapterId, content).observe(this, isSuccess -> {
                if (isSuccess) {
                    binding1.edtCommentStory.setText("");
                    binding1.edtCommentStory.clearFocus();
                    Utility.hideKeyboard(this, binding1.edtCommentStory);

                    readStoryViewModel.getReadStoryResponse(chapterId).observe(this,
                            readStoryResponse -> {
                                if (readStoryResponse != null) {
                                    comments = readStoryResponse.getComments();
                                    fetchComment(binding1);
                                }
                            });
                }
            });
        });
    }

    private void observer(int idChapter) {
        readStoryViewModel.getReadStoryResponse(idChapter).observe(this,
                readStoryResponse -> {
                    if (readStoryResponse != null) {
                        storyId = readStoryResponse.getChapter().getStory_id();
                        comments = readStoryResponse.getComments();
                        updateUIWithResponse(readStoryResponse);
                        setupChapterNavigation(readStoryResponse.getChapters(), idChapter);

                    }
                });
    }

//    private void changeColorProcessBar(LayoutCommentStoryBottomSheetBinding binding1) {
//        Drawable indeterminateDrawable = binding1.pbComment.getIndeterminateDrawable();
//        indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primary_color),
//                PorterDuff.Mode.SRC_IN);
//        binding1.pbComment.setIndeterminateDrawable(indeterminateDrawable);
//    }

    private void applyStoredColors() {
        String bgColor = SharedPrefManager.getInstance(this).getBgColor();
        String textColor = SharedPrefManager.getInstance(this).getTextColor();

        String jsBgColor = "document.body.style.background = '" + bgColor + "'";
        String jsTextColor = "document.body.style.color = '" + textColor + "'";

        binding.webView.evaluateJavascript(jsBgColor, null);
        binding.webView.evaluateJavascript(jsTextColor, null);

        binding.nestedScrollView.setBackgroundColor(Color.parseColor(bgColor));
        binding.tvNameChapter.setTextColor(Color.parseColor(textColor));
    }

    private void handleClickTextColor(View v) {
        int color = getBackgroundColor(v);
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        String js = "document.body.style.color = '" + hexColor + "'";
        binding.webView.evaluateJavascript(js, null);
        SharedPrefManager.getInstance(this).saveTextColor(hexColor);
        binding.tvNameChapter.setTextColor(color);
    }

    private void handleClickBgColor(View v) {
        int color = getBackgroundColor(v);
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        String js = "document.body.style.backgroundColor = '" + hexColor + "'";

        binding.webView.evaluateJavascript(js, value -> {
            SharedPrefManager.getInstance(this).saveBgColor(hexColor);
            binding.nestedScrollView.setBackgroundColor(color);
            String textColor = SharedPrefManager.getInstance(this).getTextColor();
            binding.tvNameChapter.setTextColor(Color.parseColor(textColor));
        });
    }

    private int getBackgroundColor(View view) {
        if (view.getBackground() instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
            return colorDrawable.getColor();
        }
        return Color.TRANSPARENT;
    }

    private void updateUIWithResponse(ReadStoryResponse readStoryResponse) {
        String nameChapter =
                readStoryResponse.getChapter().getName() + ": " + Utility.capitalizeFirstLetter(readStoryResponse.getChapter().getTitle());
        binding.tvNameChapter.setText(nameChapter);
        binding.tvTitle.setText(readStoryResponse.getChapter().getName());

        String content = readStoryResponse.getChapter().getContent();
        String fullHtml = Utility.getFullHtml(content);
        binding.webView.loadDataWithBaseURL(null, fullHtml, "text/html", "UTF-8", null);

        updateFontSize(SharedPrefManager.getInstance(this).getTextSize());

        if (SharedPrefManager.getInstance(this).checkFormatNull()) {
            saveShareReference(16, "#FFFFFF", "#000000");
        }
    }

    private void setupChapterNavigation(List<ReadStoryResponse.Chapters> chapters,
                                        int currentChapterId) {
        int idChapterPre = -1;
        int idChapterNext = -1;

        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getId() == currentChapterId) {
                if (i == 0) {
                    binding.ivChapterPre.setVisibility(View.GONE);
                }
                if (i == chapters.size() - 1) {
                    binding.ivChapterNext.setVisibility(View.GONE);
                }
                if (i > 0) {
                    idChapterPre = chapters.get(i - 1).getId();
                }
                if (i < chapters.size() - 1) {
                    idChapterNext = chapters.get(i + 1).getId();
                }
                break;
            }
        }

        setupChapterNavigationButtons(idChapterPre, idChapterNext);
    }

    private void setupChapterNavigationButtons(int idChapterPre, int idChapterNext) {
        if (idChapterPre != -1) {
            binding.ivChapterPre.setOnClickListener(v -> navigateToChapter(idChapterPre));
        }
        if (idChapterNext != -1) {
            binding.ivChapterNext.setOnClickListener(v -> navigateToChapter(idChapterNext));
        }
    }

    private void navigateToChapter(int idChapter) {
        Intent intent = new Intent(this, ReadStoryActivity.class);
        intent.putExtra("idChapter", idChapter);
        startActivity(intent);
        finish();
    }

    private void processBtnFormat() {
        binding.ivFormat.setOnClickListener(v -> {
            showPopup(v);
        });
    }

    private void processBtnBack() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void showPopup(View anchorView) {

        PopupWindow popupWindow = new PopupWindow(popupView.getRoot(),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAsDropDown(anchorView, 0, 20,
                Gravity.TOP | Gravity.END | Gravity.START | Gravity.BOTTOM);

        popupView.tvTextSize.setText(String.valueOf(SharedPrefManager.getInstance(this).getTextSize()));

        popupView.buttonDecreaseFontSize.setOnClickListener(v -> {
            int fontSize = Integer.parseInt(popupView.tvTextSize.getText().toString());
            if (fontSize > 10) {
                fontSize--;
                popupView.tvTextSize.setText(String.valueOf(fontSize));
                updateFontSize(fontSize);
                SharedPrefManager.getInstance(this).saveTextSize(fontSize);
            }
        });

        popupView.buttonIncreaseFontSize.setOnClickListener(v -> {
            int fontSize = Integer.parseInt(popupView.tvTextSize.getText().toString());
            if (fontSize < 25) {
                fontSize++;
                popupView.tvTextSize.setText(String.valueOf(fontSize));
                updateFontSize(fontSize);
                SharedPrefManager.getInstance(this).saveTextSize(fontSize);
            }
        });
    }

    private void updateFontSize(int size) {
        String js = "document.body.style.fontSize = '" + size + "px'";
        binding.webView.evaluateJavascript(js, null);
    }

    private void saveShareReference(int textSize, String bgColor, String textColor) {
        SharedPrefManager.getInstance(this).saveTextSize(textSize);
        SharedPrefManager.getInstance(this).saveBgColor(bgColor);
        SharedPrefManager.getInstance(this).saveTextColor(textColor);
    }
}