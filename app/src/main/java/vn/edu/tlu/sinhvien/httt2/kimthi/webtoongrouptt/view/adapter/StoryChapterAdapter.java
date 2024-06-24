package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryChapterBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;

public class StoryChapterAdapter extends RecyclerView.Adapter<StoryChapterAdapter.StoryChapterViewHolder> {

    private List<Chapter> chapters;

    public StoryChapterAdapter(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoryChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_chapter, parent, false);
        return new StoryChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryChapterViewHolder holder, int position) {
        holder.bind(chapters.get(position));
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public static class StoryChapterViewHolder extends RecyclerView.ViewHolder {
        ItemStoryChapterBinding binding;

        public StoryChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryChapterBinding.bind(itemView);
        }

        public void bind(Chapter chapter) {
            binding.tvChapter.setText(chapter.getName() + ": ");
            binding.tvTitle.setText(chapter.getTitle());
        }
    }
}
