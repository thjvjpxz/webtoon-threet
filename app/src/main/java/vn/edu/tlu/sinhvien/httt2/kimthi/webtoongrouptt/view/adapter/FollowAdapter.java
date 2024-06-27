package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Follow;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailActivity;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder>{
    private final List<Follow> listFollow;

    public FollowAdapter(List<Follow> listFollow) {
        this.listFollow = listFollow;
    }

    @NonNull
    @Override
    public FollowAdapter.FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FollowViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_follow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAdapter.FollowViewHolder holder, int position) {
        Follow follow = listFollow.get(position);
        holder.tvName.setText(follow.getComic().getName());
        String description = cleanAndTruncate(follow.getComic().getContent(), 150);
        holder.tvDescription.setText(description);
        Glide.with(holder.ivThumbnail).load(follow.getComic().getThumbnail()).into(holder.ivThumbnail);
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("COMIC_ID", String.valueOf(follow.getComic().getId()));
            v.getContext().startActivity(intent);
        });
    }

    private String cleanAndTruncate(String text, int length) {
        String cleanText = text.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<[^>]*>", "");

        if (cleanText.length() > length) {
            cleanText = cleanText.substring(0, length) + "...";
        }

        return cleanText;
    }

    @Override
    public int getItemCount() {
        return listFollow.size();
    }

    public static class FollowViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvName, tvDescription;
        ConstraintLayout layout;
        public FollowViewHolder(@NonNull ViewGroup parent) {
            super(parent);
            ivThumbnail = parent.findViewById(R.id.ivThumbnail);
            tvName = parent.findViewById(R.id.tvName);
            tvDescription = parent.findViewById(R.id.tvDescription);
            layout = parent.findViewById(R.id.layout_item_follow);
        }
    }
}
