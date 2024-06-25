package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailActivity;

public class CommentComicAdapter extends RecyclerView.Adapter<CommentComicAdapter.CommentComicViewHolder> {
    private List<Comment> commentList;
    private Context context;
    public CommentComicAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }
    @NonNull
    @Override
    public CommentComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentComicViewHolder((ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentComicViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        Glide.with(holder.ivAvatar.getContext())
                .load(comment.getUser().getAvatar())
                .into(holder.ivAvatar);
        Glide.with(holder.ivGifBoder.getContext())
                .asGif()
                .load(comment.getUser().getLevel().getImage())
                .into(holder.ivGifBoder);
        holder.tvName.setText(comment.getUser().getName());
        holder.tvBadge.setText(comment.getUser().getLevel().getLevel());
        int color = Color.parseColor(comment.getUser().getLevel().getStyle());
        holder.tvBadge.setTextColor(color);
        GradientDrawable border = new GradientDrawable();
        border.setShape(GradientDrawable.RECTANGLE);
        border.setStroke(2, color);
        border.setCornerRadius(10);
        holder.tvBadge.setBackground(border);
        holder.tvComment.setText(parseHTML(comment.getContent())[0]);
        Glide.with(holder.ivComment.getContext())
                .load(parseHTML(comment.getContent()).length > 1 ? parseHTML(comment.getContent())[1] : null)
                .into(holder.ivComment);
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

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentComicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivComment, ivGifBoder;
        TextView tvName, tvBadge, tvComment;
        public CommentComicViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            tvComment = itemView.findViewById(R.id.tvComment);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivGifBoder = itemView.findViewById(R.id.gifBorder);
        }
    }
}
