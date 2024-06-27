package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryCmtBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Comment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;

public class StoryCmtAdapter extends RecyclerView.Adapter<StoryCmtAdapter.StoryCmtViewHolder> {
    private List<Comment> comments;

    public StoryCmtAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoryCmtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_cmt,
                parent, false);
        return new StoryCmtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryCmtViewHolder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class StoryCmtViewHolder extends RecyclerView.ViewHolder {

        private ItemStoryCmtBinding binding;

        public StoryCmtViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryCmtBinding.bind(itemView);
        }

        public void bind(Comment comment) {
            Glide.with(binding.getRoot().getContext())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(comment.getUser().getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(Constants.imgCenterCropOption)
                    .into(binding.sivAvatar);


            binding.wvName.getSettings().setJavaScriptEnabled(true);
            binding.wvLevel.getSettings().setJavaScriptEnabled(true);
            binding.wvCmtContent.getSettings().setJavaScriptEnabled(true);

            String nameUser = comment.getUser().getName();
            if (comment.getUser().getLevel() != null) {
                String linkGit = comment.getUser().getLevel().getImage();
                String levelName = comment.getUser().getLevel().getLevel();
                String color = comment.getUser().getLevel().getStyle();
                fetchName(nameUser, linkGit);
                fetchLevel(levelName, color);
            } else {
                fetchNameNotLevel(nameUser);
                fetchLevelNotLevel();
            }

            fetchCmtContent(comment.getContent());
        }

        private void fetchCmtContent(String content) {
            String htmlContent = Utility.getFullHtml(content);
            binding.wvCmtContent.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
        }

        private void fetchName(String nameUser, String linkGif) {
            String tag = "<h6 class=\"text-transparent font-bold bg-auto bg-center bg-clip-text\"" +
                    " " +
                    "style=\"background-image: url(&quot;" + linkGif + "&quot;" +
                    ");\">" + nameUser + "</h6>";
            String htmlContent = Utility.getFullHtml(tag);
            binding.wvName.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
        }

        private void fetchLevel(String levelName, String color) {
            String tag = "<span class=\"text-xs rounded px-1 border\" style=\"color: " + color +
                    "; border-color: " + color + ";\">" + levelName + "</span>";
            String htmlContent = Utility.getFullHtml(tag);
            binding.wvLevel.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
        }

        private void fetchLevelNotLevel() {
            String tag = "<span class=\"text-xs rounded px-1 border\" style=\"border-color: " +
                    "#999999 !important;\">Cấp vô danh</span>";
            String htmlContent = Utility.getFullHtml(tag);
            binding.wvLevel.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
        }

        private void fetchNameNotLevel(String nameUser) {
            String tag = "<h6 class=\"font-bold bg-auto bg-center bg-clip-text\" " +
                    "style=\"background-image: url(&quot;null&quot;); color: #0033ff\">" + nameUser + "</h6>";
            String htmlContent = Utility.getFullHtml(tag);
            binding.wvName.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
        }
    }
}
