package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStickerBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.Stickers;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder>{
    private ItemStickerBinding binding;
    private OnItemClickListener listener;
    List<Stickers> stickers;

    public interface OnItemClickListener {
        void onItemClick(Stickers stickers);
    }
    public StickerAdapter(List<Stickers> stickers, OnItemClickListener listener) {
        this.stickers = stickers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StickerAdapter.StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerAdapter.StickerViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerAdapter.StickerViewHolder holder, int position) {
        Stickers sticker = stickers.get(position);
        Glide.with(holder.itemView.getContext()).load(sticker.getUrls().get(0)).into(holder.binding.ivSticker);
        holder.binding.tvSticker.setText(sticker.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(sticker));
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    public static class StickerViewHolder extends RecyclerView.ViewHolder {
        ItemStickerBinding binding;
        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStickerBinding.bind(itemView);
        }
    }
}
