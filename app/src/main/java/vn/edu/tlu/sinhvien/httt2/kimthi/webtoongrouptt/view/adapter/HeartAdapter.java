package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Follow;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.History;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.FollowFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HistoryFragment;

public class HeartAdapter extends FragmentStateAdapter {
    List<Follow> listFollow;
    List<History> listHistory;
    public HeartAdapter(@NonNull Fragment fragmentActivity, List<Follow> listFollow, List<History> listHistory) {
        super(fragmentActivity);
        this.listFollow = listFollow;
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FollowFragment(listFollow);
            case 1:
                return new HistoryFragment(listHistory);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
