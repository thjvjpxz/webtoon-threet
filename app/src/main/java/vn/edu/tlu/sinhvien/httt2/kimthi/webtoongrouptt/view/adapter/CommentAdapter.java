package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import static vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.LevelFragment.getGradientDrawable;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemCommentBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.Comments;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    List<Comments> comments;
    User user;

    public CommentAdapter(List<Comments> comments, User user) {
        this.comments = comments;
        this.user = user;
    }

    public void setComments(List<Comments> comments, User user) {
        this.comments = comments;
        this.user = user;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comments comment = comments.get(position);

        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar())
                .circleCrop()
                .into(holder.binding.imvAvatar);
        holder.binding.tvName.setText(user.getName());

        if(user.getLevel() == null) {
            holder.binding.tvLevel.setText("Sơ cấp");
        } else {
            holder.binding.tvLevel.setText(user.getLevel().getLevel());
            GradientDrawable originalDrawable = (GradientDrawable) holder.binding.tvLevel.getBackground().mutate();
            GradientDrawable newDrawable = getGradientDrawable(originalDrawable);

            int color = Color.parseColor(user.getLevel().getStyle());
            newDrawable.setStroke(2, color);
            holder.binding.tvLevel.setBackground(newDrawable);

            holder.binding.tvLevel.setTextColor(color);
        }
        String[] result = parseHTML(comment.getContent());

        if (result.length == 2) {
            Glide.with(holder.itemView.getContext()).load(result[1]).into(holder.binding.imvInfo);
            holder.binding.tvComment.setText(result[0]);
        } else if (result.length == 1) {
            if (comment.getContent().contains("<img") && comment.getContent().contains("<p>")) {
                Glide.with(holder.itemView.getContext()).load(result[0]).into(holder.binding.imvInfo);
                holder.binding.tvComment.setVisibility(View.GONE);
            } else {
                holder.binding.tvComment.setText(result[0]);
                holder.binding.imvInfo.setVisibility(View.GONE);
            }
        } else {
            holder.binding.tvComment.setText(comment.getContent());
        }

        if (comment.getComic() != null) {
            holder.binding.tvComic.setText(comment.getComic().getName());
        } else if (comment.getStory() != null) {
            holder.binding.tvComment.setText(comment.getStory().getName());
        } else {
            holder.binding.tvComment.setText("NULL");
        }
        holder.binding.tvTime.setText(comment.getCreated_at());
        holder.binding.tvLike.setText(comment.getLike().toString());
        holder.binding.tvDisLike.setText(comment.getDislike().toString());
        holder.binding.tvReport.setText(comment.getReport().toString());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCommentBinding.bind(itemView);
        }
    }

    public static String[] parseHTML(String html) {
        Pattern commentPattern = Pattern.compile(">([^<]+)<");
        Pattern urlPattern = Pattern.compile("src=\"([^\"]+)\"");

        Matcher commentMatcher = commentPattern.matcher(html);
        String comment = commentMatcher.find() ? commentMatcher.group(1).trim() : null;

        Matcher urlMatcher = urlPattern.matcher(html);
        String urlImage = urlMatcher.find() ? urlMatcher.group(1) : null;

        if (comment != null && !comment.isEmpty() && urlImage != null) {
            return new String[]{comment, urlImage};
        } else if (comment != null && !comment.isEmpty()) {
            return new String[]{comment};
        } else if (urlImage != null) {
            return new String[]{urlImage};
        } else {
            return new String[]{"No comment or image found"};
        }
    }
}
