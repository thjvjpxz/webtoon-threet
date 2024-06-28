package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.ChaptersStoryListFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.CmtStoryListFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.IntroStoryFragment;

public class TabDetailStoryAdapter extends FragmentStateAdapter {
    private Story story;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    public TabDetailStoryAdapter(@NonNull FragmentManager fragmentManager,
                                 @NonNull Lifecycle lifecycle, Story story) {
        super(fragmentManager, lifecycle);
        this.story = story;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new IntroStoryFragment(story);
        } else if (position == 1) {
            fragment = new ChaptersStoryListFragment(story);
        } else {
            fragment = new CmtStoryListFragment(story);
        }
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull FragmentViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        registeredFragments.remove(holder.getAdapterPosition());
    }

}
