package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.ChaptersStoryListFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.CmtStoryListFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.IntroStoryFragment;

public class TabDetailStoryAdapter extends FragmentStateAdapter {
    private Story story;
    private List<String> history;

    public TabDetailStoryAdapter(@NonNull FragmentManager fragmentManager,
                                 @NonNull Lifecycle lifecycle, Story story) {
        super(fragmentManager, lifecycle);
        this.story = story;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new IntroStoryFragment(story);
        } else if (position == 1) {
            return new ChaptersStoryListFragment(story);
        } else {
            return new CmtStoryListFragment(story);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
