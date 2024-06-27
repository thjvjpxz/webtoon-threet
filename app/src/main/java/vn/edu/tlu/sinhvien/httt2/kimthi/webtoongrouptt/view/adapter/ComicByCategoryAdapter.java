package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Author;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailActivity;

public class ComicByCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Comic> comicList;

    public ComicByCategoryAdapter(List<Comic> comicList) {
        this.comicList = comicList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            return new ComicByCategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_comic, parent, false));
        }else{
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ComicByCategoryViewHolder) {
            ComicByCategoryViewHolder holder1 = (ComicByCategoryViewHolder) holder;
            Comic comic = comicList.get(position);
            if(comic != null){
                holder1.tvTitle.setText(comic.getName());
                List<Author> authors = comic.getAuthors();
                if (authors != null && !authors.isEmpty()) {
                    holder1.tvAuthor.setText(authors.get(0).getName());
                } else {
                    holder1.tvAuthor.setText("Đang cập nhật");
                }
                holder1.tvStatus.setText(comic.getStatus());
                holder1.tvUpdated.setText(comic.getDate_updated());
                holder1.tvViews.setText(String.valueOf(comic.getTotal_views()));
                holder1.tvTotalChap.setText(String.valueOf(comic.getChapters_count()));
                Glide.with(holder1.ivThumbnail).load(comic.getThumbnail()).into(holder1.ivThumbnail);
                holder1.item_comic.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("COMIC_ID", String.valueOf(comic.getId()));
                    v.getContext().startActivity(intent);
                });
            }
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return comicList == null ? 0 : comicList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return comicList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void addLoading() {
        comicList.add(null);
        notifyItemInserted(comicList.size() - 1);
    }

    public void removeLoading() {
        int position = comicList.size() - 1;
        if (comicList.get(position) == null) {
            comicList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private static class ComicByCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvAuthor, tvStatus, tvUpdated, tvViews, tvTotalChap;
        ConstraintLayout item_comic;
        public ComicByCategoryViewHolder(@NonNull View parent) {
            super(parent);
            ivThumbnail = parent.findViewById(R.id.ivThumbnail);
            tvTitle = parent.findViewById(R.id.tvTitle);
            tvAuthor = parent.findViewById(R.id.tvAuthor);
            tvStatus = parent.findViewById(R.id.tvStatus);
            tvUpdated = parent.findViewById(R.id.tvUpdated);
            tvViews = parent.findViewById(R.id.tvViews);
            tvTotalChap = parent.findViewById(R.id.tvTotalChap);
            item_comic = parent.findViewById(R.id.item_comic);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
