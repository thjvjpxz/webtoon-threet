package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.UserFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HeartFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HomeFragment;

public class NavigationBarAdapter extends FragmentStateAdapter {

    public NavigationBarAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new HeartFragment();
        } else {
            return new CompassFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
