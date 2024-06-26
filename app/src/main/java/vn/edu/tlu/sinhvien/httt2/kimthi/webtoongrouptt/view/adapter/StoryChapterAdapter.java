package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemStoryChapterBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Chapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Utility;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.ReadStoryActivity;

public class StoryChapterAdapter extends RecyclerView.Adapter<StoryChapterAdapter.StoryChapterViewHolder> {

    private List<Chapter> chapters;
    private List<Chapter> chaptersFull;
    private Story story;

    public StoryChapterAdapter(List<Chapter> chapters, Story story) {
        this.chapters = chapters;
        chaptersFull = new ArrayList<>(chapters);
        this.story = story;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        chaptersFull = new ArrayList<>(chapters);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoryChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_chapter,
                parent, false);
        return new StoryChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryChapterViewHolder holder, int position) {
        holder.bind(chapters.get(position), story);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public List<Chapter> getChaptersFull() {
        return chaptersFull;
    }

    public void filter(String text) {
        chapters.clear();
        if (text.isEmpty()) {
            chapters.addAll(chaptersFull);
        } else {
            for (Chapter item : chaptersFull) {
                if (item.getChapterNumber().contains(text)) {
                    chapters.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class StoryChapterViewHolder extends RecyclerView.ViewHolder {
        ItemStoryChapterBinding binding;

        public StoryChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStoryChapterBinding.bind(itemView);
        }

        public void bind(Chapter chapter, Story story) {
            binding.tvChapter.setText(chapter.getName() + ": ");
            binding.tvTitle.setText(Utility.capitalizeFirstLetter(chapter.getTitle()));
            binding.llItem.setOnClickListener(v -> {
                String slug_chapter = chapter.getSlug();
                String slug_story = story.getSlug();

                Intent intent = new Intent(v.getContext(), ReadStoryActivity.class);
                intent.putExtra("slug_chapter", slug_chapter);
                intent.putExtra("slug_story", slug_story);
                v.getContext().startActivity(intent);
            });
        }
    }
}
