package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import java.util.ArrayList;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentCmtStoryListBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StoryCmtAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.DetailStoryViewModel;

public class CmtStoryListFragment extends Fragment {
    private FragmentCmtStoryListBinding binding;
    private Story story;
    private DetailStoryViewModel detailStoryViewModel;
    private StoryCmtAdapter storyCmtAdapter;

    public CmtStoryListFragment(Story story) {
        this.story = story;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCmtStoryListBinding.inflate(inflater, container, false);

        // Tùy chỉnh màu của ProgressBar
        Drawable indeterminateDrawable = binding.pbComment.getIndeterminateDrawable();
        indeterminateDrawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.primary_color), PorterDuff.Mode.SRC_IN);
        binding.pbComment.setIndeterminateDrawable(indeterminateDrawable);

        detailStoryViewModel = new ViewModelProvider(this).get(DetailStoryViewModel.class);
        storyCmtAdapter = new StoryCmtAdapter(new ArrayList<>());
        observer();
        binding.rvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvComment.setAdapter(storyCmtAdapter);


        return binding.getRoot();
    }

    private void observer() {
        detailStoryViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.pbComment.setVisibility(View.VISIBLE);
            } else {
                binding.pbComment.setVisibility(View.GONE);
            }
        });
        detailStoryViewModel.getHasComments().observe(getViewLifecycleOwner(), hasComments -> {
            if (hasComments) {
                binding.tvNotComment.setVisibility(View.GONE);
            } else {
                binding.tvNotComment.setVisibility(View.VISIBLE);
            }
        });
        detailStoryViewModel.getDetailStoryResponse(story.getId()).observe(getViewLifecycleOwner(), detailStoryResponse -> {
            if (detailStoryResponse != null) {
                storyCmtAdapter.setComments(detailStoryResponse.getComments());
            }
        });

    }
}