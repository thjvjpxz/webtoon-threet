package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentStoriesConvertBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.TypeStoryConst;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StoriesByTypeAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.StoryHomeViewModel;

public class StoriesConvertFragment extends Fragment {
    private FragmentStoriesConvertBinding binding;
    private StoriesByTypeAdapter adapter;
    private StoryHomeViewModel viewModel;
    public StoriesConvertFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStoriesConvertBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(StoryHomeViewModel.class);
        adapter = new StoriesByTypeAdapter(new ArrayList<>(), TypeStoryConst.TYPE_CONVERT);

        binding.rvStoriesByType.setLayoutManager(new LinearLayoutManager(getContext()));
        observer();

        return binding.getRoot();
    }

    private void observer() {
        String type = TypeStoryConst.TYPE_STORY[TypeStoryConst.TYPE_CONVERT];
        viewModel.fetchStoriesByTypeData(type).observe(getViewLifecycleOwner(), stories -> {
            if (stories != null) {
                adapter.setStories(stories.getStories().getData());
                binding.rvStoriesByType.setAdapter(adapter);
            }
        });
    }
}