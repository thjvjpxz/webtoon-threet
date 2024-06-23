package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryHoriBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryHotBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.TypeStoryConst;

public class StoryHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Story> stories;
    private ViewPager2 viewPager2;
    private int typeStoryShow;

    public StoryHomeAdapter(List<Story> stories, int typeStoryShow) {
        this.stories = stories;
        this.typeStoryShow = typeStoryShow;
    }

    public StoryHomeAdapter(List<Story> stories, ViewPager2 viewPager2, int typeStoryShow) {
        this.viewPager2 = viewPager2;
        this.stories = stories;
        this.typeStoryShow = typeStoryShow;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (typeStoryShow == TypeStoryConst.TYPE_HOT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_hot,
                    parent, false);
            return new StoryHotViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_hori,
                    parent, false);
            return new StoryHoriViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (typeStoryShow == TypeStoryConst.TYPE_HOT) {
            StoryHotViewHolder storyHotViewHolder = (StoryHotViewHolder) holder;
            storyHotViewHolder.bind(stories.get(position));
            if (position == stories.size() - 2) {
                viewPager2.post(() -> {
                    stories.addAll(stories);
                    notifyDataSetChanged();
                });
            }
        } else {
            StoryHoriViewHolder storyHoriViewHolder = (StoryHoriViewHolder) holder;
            storyHoriViewHolder.bind(stories.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static class StoryHotViewHolder extends RecyclerView.ViewHolder {
        ItemStoryHotBinding binding;

        public StoryHotViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryHotBinding.bind(itemView);
        }

        public void bind(Story story) {
            binding.txtName.setText(story.getName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions =
                    requestOptions.transform(new CenterCrop(), new RoundedCorners(30))
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(story.getThumbnail())
                    .apply(requestOptions)
                    .into(binding.imgAvatar);
        }
    }

    public static class StoryHoriViewHolder extends RecyclerView.ViewHolder {
        ItemStoryHoriBinding binding;

        public StoryHoriViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryHoriBinding.bind(itemView);
        }

        public void bind(Story story) {
            binding.txtStoryName.setText(story.getName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(story.getThumbnail())
                    .apply(requestOptions)
                    .into(binding.imgThumbnail);
        }
    }

}
