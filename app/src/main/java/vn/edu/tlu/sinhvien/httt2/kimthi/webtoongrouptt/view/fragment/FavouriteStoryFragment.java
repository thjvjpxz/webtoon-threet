package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentFavouriteStoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.FavouriteStoryResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.FavAndHisAdapter;

public class FavouriteStoryFragment extends Fragment {

    private FragmentFavouriteStoryBinding binding;
    private List<FavouriteStoryResponse.Follow> follows;
    private FavAndHisAdapter adapter;

    public FavouriteStoryFragment(List<FavouriteStoryResponse.Follow> follows) {
        this.follows = follows;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteStoryBinding.inflate(inflater, container, false);

        adapter = new FavAndHisAdapter(follows);
        binding.rvFavouriteStory.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvFavouriteStory.setAdapter(adapter);

        return binding.getRoot();
    }
}