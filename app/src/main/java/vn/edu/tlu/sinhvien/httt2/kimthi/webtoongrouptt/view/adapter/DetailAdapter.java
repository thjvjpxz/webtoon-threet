package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Chapter> listChapter;
    public DetailAdapter(List<Chapter> listChapter) {
        this.listChapter = listChapter;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chapter chapter = listChapter.get(position);
        DetailViewHolder detailViewHolder = (DetailViewHolder) holder;
        detailViewHolder.chapter_number.setText("Chapter " + chapter.getName());
        detailViewHolder.chapter_title.setText(chapter.getTitle());
        Glide.with(detailViewHolder.chapter_image).load(chapter.getImage()).into(detailViewHolder.chapter_image);
    }

    @Override
    public int getItemCount() {
        return listChapter.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView chapter_number;
        TextView chapter_title;
        ImageView chapter_image;
        public DetailViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);
            chapter_number = itemView.findViewById(R.id.chapter_number);
            chapter_title = itemView.findViewById(R.id.chapter_title);
            chapter_image = itemView.findViewById(R.id.chapter_image);
        }
    }
}
