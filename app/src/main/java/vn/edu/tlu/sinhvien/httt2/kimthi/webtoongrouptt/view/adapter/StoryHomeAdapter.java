package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryHotBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.StoryHomeResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;

public class StoryHomeAdapter extends RecyclerView.Adapter<StoryHomeAdapter.StoryHomeViewHolder> {
    private List<Story> stories;
    private ViewPager2 viewPager2;

    public StoryHomeAdapter(List<Story> stories, ViewPager2 viewPager2) {
        this.viewPager2 = viewPager2;
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_hot, parent, false);
        return new StoryHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHomeViewHolder holder, int position) {
        holder.bind(stories.get(position));
        if (position == stories.size() - 2) {
            viewPager2.post(() -> {
                stories.addAll(stories);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class StoryHomeViewHolder extends RecyclerView.ViewHolder {
        ItemStoryHotBinding binding;
        public StoryHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryHotBinding.bind(itemView);
        }

        public void bind(Story story) {
            binding.txtName.setText(story.getName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
            Glide.with(itemView.getContext())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(story.getThumbnail())
                    .apply(requestOptions)
                    .into(binding.imgAvatar);
        }
    }
}
