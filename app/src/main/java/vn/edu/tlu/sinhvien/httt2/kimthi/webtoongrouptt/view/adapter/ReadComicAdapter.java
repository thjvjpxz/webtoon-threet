package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Image;

public class ReadComicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Image> images;
    public ReadComicAdapter(List<Image> images) {
        this.images = images;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReadComicViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Image image = images.get(position);
        ReadComicViewHolder readComicViewHolder = (ReadComicViewHolder) holder;
        Glide.with(readComicViewHolder.ivImage).load(image.getLink()).into(readComicViewHolder.ivImage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ReadComicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        public ReadComicViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
