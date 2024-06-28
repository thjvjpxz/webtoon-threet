package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.FavouriteStoryFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HistoryStoryFragment;

public class FavouriteAndHistoryTabAdapter extends FragmentStateAdapter {
    private List<FavouriteStoryResponse.History> histories;
    private List<FavouriteStoryResponse.Follow> follows;

    public FavouriteAndHistoryTabAdapter(@NonNull Fragment fragment,
                                         List<FavouriteStoryResponse.History> histories,
                                         List<FavouriteStoryResponse.Follow> follows) {
        super(fragment);
        this.histories = histories;
        this.follows = follows;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new FavouriteStoryFragment(follows);
        } else {
            return new HistoryStoryFragment(histories);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
