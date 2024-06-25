package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentFollowBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Follow;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.FollowAdapter;

public class FollowFragment extends Fragment {
    FragmentFollowBinding binding;
    List<Follow> listFollow;

    public FollowFragment(List<Follow> listFollow) {
        this.listFollow = listFollow;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowBinding.inflate(inflater, container, false);
        binding.rvFollow.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFollow.setAdapter(new FollowAdapter(listFollow));

        return binding.getRoot();
    }
}