package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryVerBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;

public class StoriesByTypeAdapter extends RecyclerView.Adapter<StoriesByTypeAdapter.StoriesByTypeViewHolder> {

    private List<Story> stories;
    private int type;

    public StoriesByTypeAdapter(List<Story> stories, int type) {
        this.stories = stories;
        this.type = type;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoriesByTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_ver, parent, false);
        return new StoriesByTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesByTypeViewHolder holder, int position) {
        holder.bind(stories.get(position));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    static class StoriesByTypeViewHolder extends RecyclerView.ViewHolder {
        ItemStoryVerBinding binding;

        public StoriesByTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryVerBinding.bind(itemView);
        }

        public void bind(Story story) {
            Glide.with(binding.getRoot().getContext())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(story.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(Constants.IMG_CENTER_CROP_ROUND_30_OPTIONS)
                    .into(binding.imgThumbnail);

            binding.tvNameStory.setText(story.getName());
            binding.tvView.setText(String.valueOf(story.getTotal_views()));
        }
    }
}
