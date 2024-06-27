package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.FavAndHisFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.UserFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HeartFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HomeFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.StoryHomeFragment;

public class NavigationBarAdapter extends FragmentStateAdapter {

    private static int TYPE_WEBTOON;

    public NavigationBarAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int TYPE_WEBTOON) {
        super(fragmentManager, lifecycle);
        this.TYPE_WEBTOON = TYPE_WEBTOON;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (TYPE_WEBTOON == Constants.TYPE_WEBTOON_COMIC) {
            if (position == 0) {
                return new HomeFragment();
            } else if (position == 1) {
                return new HeartFragment();
            } else {
                return new UserFragment();
            }
        }
        else if (TYPE_WEBTOON == Constants.TYPE_WEBTOON_STORY) {
            if (position == 0) {
                return new StoryHomeFragment();
            } else if (position == 1) {
                return new FavAndHisFragment();
            } else {
                return new UserFragment();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
