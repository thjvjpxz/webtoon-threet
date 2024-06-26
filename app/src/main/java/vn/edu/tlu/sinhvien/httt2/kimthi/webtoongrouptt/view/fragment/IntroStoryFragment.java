package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentIntroStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;

public class IntroStoryFragment extends Fragment {
    private FragmentIntroStoryBinding binding;
    private Story story;

    public IntroStoryFragment(Story story) {
        this.story = story;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIntroStoryBinding.inflate(inflater, container, false);

        fillData();

        return binding.getRoot();
    }

    private void fillData() {
        binding.tvNameStory2.setText(story.getOrigin_name() == null ? "Không có" :
                story.getOrigin_name());
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

        String content = "Giới thiệu: ";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content += story.getContent() != null ? Html.fromHtml(story.getContent(),
                    Html.FROM_HTML_MODE_COMPACT) : "Truyện rất hay, vui lòng đọc để biết thêm chi" +
                    " tiết!";
        } else {
            content += story.getContent() != null ? Html.fromHtml(story.getContent()) : "Truyện " +
                    "rất hay, vui lòng đọc để biết thêm chi tiết!";
        }

        binding.tvIntro.setText(content);
    }

//    private void processShowMore() {
//        binding.btnShowMore.setOnClickListener(v -> {
//            boolean isSeleted = !binding.btnShowMore.isSelected();
//            binding.btnShowMore.setSelected(isSeleted);
//            if (isSeleted) {
//                binding.tvIntro.setMaxLines(Integer.MAX_VALUE);
//                binding.tvIntro.setEllipsize(null);
//                binding.btnShowMore.setImageResource(R.drawable.baseline_arrow_drop_up_24);
//            } else {
//                binding.tvIntro.setMaxLines(3);
//                binding.tvIntro.setEllipsize(TextUtils.TruncateAt.END);
//                binding.btnShowMore.setImageResource(R.drawable.baseline_arrow_drop_down_24);
//            }
//        });
//    }

}