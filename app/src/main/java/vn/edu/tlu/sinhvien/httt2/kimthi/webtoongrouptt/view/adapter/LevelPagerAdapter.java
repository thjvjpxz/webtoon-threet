package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.logging.Level;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.User;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HeartFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.LevelFragment;

public class LevelPagerAdapter extends FragmentStateAdapter {

    private final Bundle bundle;

    public LevelPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
        super(fragmentActivity);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        LevelFragment levelFragment = new LevelFragment();
        levelFragment.setArguments(bundle);
        return levelFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
