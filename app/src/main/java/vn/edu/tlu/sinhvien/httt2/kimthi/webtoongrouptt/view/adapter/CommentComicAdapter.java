package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
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

import java.util.ArrayList;
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
        holder.tvLike.setText(String.valueOf(comment.getLike()));
        holder.tvDislike.setText(String.valueOf(comment.getDislike()));
        holder.tvBadge.setText(comment.getUser().getLevel().getLevel());
        int color = Color.parseColor(comment.getUser().getLevel().getStyle());
        holder.tvBadge.setTextColor(color);
        GradientDrawable border = new GradientDrawable();
        border.setShape(GradientDrawable.RECTANGLE);
        border.setStroke(2, color);
        border.setCornerRadius(10);
        holder.tvBadge.setBackground(border);
        List<String> parsedContent = parseHTML(comment.getContent());
        holder.tvComment.setText(parsedContent.get(0));
        if (parsedContent.size() > 1) {
            Glide.with(holder.ivComment.getContext())
                    .load(parsedContent.get(1))
                    .override(200, 200)
                    .into(holder.ivComment);
        } else {
            holder.ivComment.setImageDrawable(null);
        }
        holder.ivLike.setOnClickListener(v -> {
            ((DetailActivity) context).likeComment(String.valueOf(comment.getId()));
        });

        holder.ivDislike.setOnClickListener(v -> {
            ((DetailActivity) context).dislikeComment(String.valueOf(comment.getId()));
        });

        holder.tvReport.setOnClickListener(v -> {
            ((DetailActivity) context).reportComment(String.valueOf(comment.getId()));
        });
    }

    public static List<String> parseHTML(String html) {
        Pattern commentPattern = Pattern.compile(">([^<]+)<");

        Pattern urlPattern = Pattern.compile("src=\"([^\"]+)\"");

        Matcher commentMatcher = commentPattern.matcher(html);
        Matcher urlMatcher = urlPattern.matcher(html);

        List<String> results = new ArrayList<>();

        while (commentMatcher.find()) {
            String comment = commentMatcher.group(1).trim();
            if (!comment.isEmpty()) {
                results.add(comment);
            }
        }

        while (urlMatcher.find()) {
            String urlImage = urlMatcher.group(1);
            results.add(urlImage);
        }

        String unwrappedTextPattern = "(?<!>)([^<>]+)(?!<)";
        Matcher unwrappedTextMatcher = Pattern.compile(unwrappedTextPattern).matcher(html);
        while (unwrappedTextMatcher.find()) {
            String unwrappedText = unwrappedTextMatcher.group(1).trim();
            if (!unwrappedText.isEmpty()) {
                results.add(unwrappedText);
            }
        }

        if (results.isEmpty()) {
            results.add("No comment or image found");
        }

        return results;
    }


    public void addComment(Comment comment) {
        this.commentList.add(0, comment);
        notifyItemInserted(0);
    }

    public Comment getCommentById(String commentId) {
        for (Comment comment : commentList) {
            if (String.valueOf(comment.getId()).equals(commentId)) {
                return comment;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentComicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivComment, ivGifBoder, ivLike, ivDislike;
        TextView tvName, tvBadge, tvComment, tvLike, tvDislike, tvReport;
        public CommentComicViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            tvComment = itemView.findViewById(R.id.tvComment);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivGifBoder = itemView.findViewById(R.id.gifBorder);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvDislike = itemView.findViewById(R.id.tvDislike);
            tvReport = itemView.findViewById(R.id.tvReport);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivDislike = itemView.findViewById(R.id.ivDislike);
        }
    }
}
