package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemDialogStickerBinding;

public class StickerDialogAdapter extends RecyclerView.Adapter<StickerDialogAdapter.ViewHolder> {

    private List<String> stickers;
    private ItemDialogStickerBinding binding;

    public StickerDialogAdapter(List<String> stickers) {
        this.stickers = stickers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemDialogStickerBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemDialogStickerBinding.bind(itemView);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_sticker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String stickerUrl = stickers.get(position);
        Glide.with(holder.itemView.getContext()).load(stickerUrl).into(holder.binding.imvSticker);
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }
}
