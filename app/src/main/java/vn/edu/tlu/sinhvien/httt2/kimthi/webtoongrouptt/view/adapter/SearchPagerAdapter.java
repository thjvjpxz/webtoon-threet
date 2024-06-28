package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SearchActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.SearchFragment;

public class SearchPagerAdapter extends FragmentStateAdapter {
    private final SearchActivity searchActivity;

    public SearchPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.searchActivity = (SearchActivity) fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String query = searchActivity.getQuery();
        String sort = searchActivity.getSort();
        int type = searchActivity.getType();
        return SearchFragment.newInstance(position, query, sort, type);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
