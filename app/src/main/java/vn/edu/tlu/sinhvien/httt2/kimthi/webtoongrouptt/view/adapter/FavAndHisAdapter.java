package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryFavBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryHoriBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailStoryActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.FavAndHisFragment;

public class FavAndHisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FavouriteStoryResponse.Follow> follows;
    private List<FavouriteStoryResponse.History> histories;
    private int type;

    public FavAndHisAdapter(List<FavouriteStoryResponse.Follow> follows) {
        this.follows = follows;
        type = Constants.TYPE_FAVOURITE_STORY;
    }

    public FavAndHisAdapter(List<FavouriteStoryResponse.History> histories, int type) {
        this.histories = histories;
        this.type = type;
    }

    public void setFavourites(List<FavouriteStoryResponse.Follow> follows) {
        this.follows = follows;
        notifyDataSetChanged();
    }

    public void setHistories(List<FavouriteStoryResponse.History> histories) {
        this.histories = histories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == Constants.TYPE_FAVOURITE_STORY) {
            return new FavouriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_hori, parent, false));
        } else {
            return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_fav, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (type == Constants.TYPE_FAVOURITE_STORY) {
            ((FavouriteViewHolder) holder).bind(follows.get(position));
        } else {
            ((HistoryViewHolder) holder).bind(histories.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (type == Constants.TYPE_FAVOURITE_STORY) {
            return follows.size();
        } else {
            return histories.size();
        }
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ItemStoryFavBinding binding;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryFavBinding.bind(itemView);
        }

        public void bind(FavouriteStoryResponse.History history) {
            Glide.with(binding.getRoot())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(history.getStory().getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(Constants.IMG_CENTER_CROP_ROUND_30_OPTIONS)
                    .into(binding.imgThumbnail);
            binding.tvNameStory.setText(history.getStory().getName());
            binding.tvChapLatest.setText("Mới nhất: " + history.getLastChapter().getName());
            binding.tvChapLastRead.setText("Đang đọc: " + history.getLastRead().getName());

            binding.rlItemStoryFav.setOnClickListener(v -> {
                Story story = history.getStory();
                Intent intent = new Intent(binding.getRoot().getContext(),
                        DetailStoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("story", story);
                intent.putExtras(bundle);
                binding.getRoot().getContext().startActivity(intent);

                Context context = binding.getRoot().getContext();
                if (context instanceof MainActivity) {
                    ((MainActivity) context).finish();
                }
            });
        }

    }

    static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        ItemStoryHoriBinding binding;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryHoriBinding.bind(itemView);
        }

        public void bind(FavouriteStoryResponse.Follow follow) {
            Glide.with(binding.getRoot())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(follow.getStory().getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(Constants.IMG_CENTER_CROP_ROUND_30_OPTIONS)
                    .into(binding.imgThumbnail);
            binding.txtStoryName.setText(follow.getStory().getName());
            binding.linearLayout2.setOnClickListener(v -> {
                Story story = follow.getStory();
                Intent intent = new Intent(binding.getRoot().getContext(),
                        DetailStoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("story", story);
                intent.putExtras(bundle);
                binding.getRoot().getContext().startActivity(intent);

                Context context = binding.getRoot().getContext();
                if (context instanceof MainActivity) {
                    ((MainActivity) context).finish();
                }
            });
        }
    }

}
