package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.StoriesCompleteFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.StoriesConvertFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.StoriesHotFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.StoriesTranslationFragment;

public class TabTypeStoryAdapter extends FragmentStateAdapter {
    public TabTypeStoryAdapter(@NonNull FragmentManager fragmentManager,
                               @NonNull Lifecycle lifecycle
    ) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new StoriesHotFragment();
        } else if (position == 1) {
            return new StoriesCompleteFragment();
        } else if (position == 2) {
            return new StoriesConvertFragment();
        } else {
            return new StoriesTranslationFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
