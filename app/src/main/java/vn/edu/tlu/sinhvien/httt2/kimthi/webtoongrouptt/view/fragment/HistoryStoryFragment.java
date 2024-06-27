package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentHistoryStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.FavAndHisAdapter;

public class HistoryStoryFragment extends Fragment {

    private FragmentHistoryStoryBinding binding;
    private List<FavouriteStoryResponse.History> histories;
    private FavAndHisAdapter adapter;

    public HistoryStoryFragment(List<FavouriteStoryResponse.History> histories) {
        this.histories = histories;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryStoryBinding.inflate(inflater, container, false);

        adapter = new FavAndHisAdapter(histories, Constants.TYPE_HISTORY_STORY);
        binding.rvHistoryStory.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.rvHistoryStory.setAdapter(adapter);

        return binding.getRoot();
    }
}