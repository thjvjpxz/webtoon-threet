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
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentHistoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.History;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.HistoryAdapter;

public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;
    List<History> listHistory;

    public HistoryFragment(List<History> listHistory) {
        this.listHistory = listHistory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHistory.setAdapter(new HistoryAdapter(listHistory));

        return binding.getRoot();
    }
}