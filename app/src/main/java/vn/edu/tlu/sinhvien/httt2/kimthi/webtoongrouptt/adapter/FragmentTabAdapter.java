package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.LoginFragment;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.RegisterFragment;

public class FragmentTabAdapter extends FragmentStateAdapter {

    public FragmentTabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new RegisterFragment();
        } else {
            return new LoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
