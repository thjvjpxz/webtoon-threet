package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.annotation.SuppressLint;
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
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.History;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailActivity;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    List<History> listHistory;

    public HistoryAdapter(List<History> listHistory) {
        this.listHistory = listHistory;
    }
    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_history, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        History history = listHistory.get(position);
        holder.tvName.setText(history.getComic().getName());
        holder.tvLastChapter.setText("Chapter " + history.getLastChapter().getName());
        holder.tvLastRead.setText("Chapter " + history.getLastRead().getName());
        Glide.with(holder.ivThumbnail.getContext()).load(history.getComic().getThumbnail()).into(holder.ivThumbnail);
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("COMIC_ID", String.valueOf(history.getComic().getId()));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvName, tvLastChapter, tvLastRead;
        ConstraintLayout layout;
        public HistoryViewHolder(@NonNull ViewGroup parent) {
            super(parent);
            ivThumbnail = parent.findViewById(R.id.ivThumbnail);
            tvName = parent.findViewById(R.id.tvName);
            tvLastChapter = parent.findViewById(R.id.tvLastChapter);
            tvLastRead = parent.findViewById(R.id.tvLastRead);
            layout = parent.findViewById(R.id.layout_item_history);
        }
    }
}
