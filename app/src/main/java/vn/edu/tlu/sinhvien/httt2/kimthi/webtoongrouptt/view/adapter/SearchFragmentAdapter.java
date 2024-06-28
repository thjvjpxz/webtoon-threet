package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemCommentBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemRecycviewSearchBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.OnItemSearchClickListener;

public class SearchFragmentAdapter extends RecyclerView.Adapter<SearchFragmentAdapter.SearchFragmentViewHolder> {
    List<Comic> comics;
    private int type;
    private OnItemSearchClickListener.OnItemClickListener listener;
    private ItemRecycviewSearchBinding binding;

    public SearchFragmentAdapter(List<Comic> comics, int type, OnItemSearchClickListener.OnItemClickListener listener) {
        this.comics = comics;
        this.type = type;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycview_search, parent, false);
        return new SearchFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFragmentViewHolder holder, int position) {
        Comic comic = comics.get(position);
        Glide.with(holder.itemView.getContext())
                .load(comic.getThumbnail())
                .into(holder.binding.imvAvatar);
        String nameComic = comic.getName();
        if (nameComic.length() > 17) {
            nameComic = nameComic.substring(0, 17) + "...";
        }
        holder.binding.tvName.setText(nameComic);
        String timeAgo = getTimeAgo(comic.getChapter().getCreated_at());
        if (type == 0) {
            holder.binding.tvChapter.setText(String.format("Chương %s", comic.getChapter().getName()));
            holder.binding.tvTime.setText(timeAgo);
        } else {
            timeAgo = getTimeAgoFromISO(comic.getDate_updated());
            holder.binding.tvChapter.setText(String.format(comic.getChapter().getName()));
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(comic));
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public static class SearchFragmentViewHolder extends RecyclerView.ViewHolder {
        ItemRecycviewSearchBinding binding;
        public SearchFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecycviewSearchBinding.bind(itemView);
        }
    }

    public String getTimeAgo(String timeString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date past = format.parse(timeString);
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if (seconds < 60) {
                return seconds + " giây trước";
            } else if (minutes < 60) {
                return minutes + " phút trước";
            } else if (hours < 24) {
                return hours + " giờ trước";
            } else {
                return days + " ngày trước";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getTimeAgoFromISO(String timeString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault());
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date past = format.parse(timeString);
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if (seconds < 60) {
                return seconds + " giây trước";
            } else if (minutes < 60) {
                return minutes + " phút trước";
            } else if (hours < 24) {
                return hours + " giờ trước";
            } else {
                return days + " ngày trước";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
