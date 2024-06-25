package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.ChaptersStoryListFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.IntroStoryFragment;

public class TabDetailStoryAdapter extends FragmentStateAdapter {
    private Story story;
    public TabDetailStoryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Story story) {
        super(fragmentManager, lifecycle);
        this.story = story;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new IntroStoryFragment(story);
        } else {
            return new ChaptersStoryListFragment(story);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
