package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentChaptersStoryListBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StoryChapterAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.DetailStoryViewModel;

public class ChaptersStoryListFragment extends Fragment {

    private FragmentChaptersStoryListBinding binding;
    private Story story;
    private DetailStoryViewModel viewModel;
    private StoryChapterAdapter storyChapterAdapter;


    public ChaptersStoryListFragment(Story story) {
        this.story = story;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChaptersStoryListBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(DetailStoryViewModel.class);
        storyChapterAdapter = new StoryChapterAdapter(new ArrayList<>());
        observer(story);
        binding.rvChapters.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChapters.setAdapter(storyChapterAdapter);

        customSearchView();
        handleFindChapter();

        return binding.getRoot();
    }

    private void handleFindChapter() {
        binding.svChapter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    storyChapterAdapter.setChapters(storyChapterAdapter.getChaptersFull());
                } else {
                    storyChapterAdapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                storyChapterAdapter.filter(newText);
                return false;
            }
        });
    }

    private void observer(Story story) {
        viewModel.getDetailStoryResponse(story.getId()).observe(getViewLifecycleOwner(),
                detailStoryResponse -> {
            if (detailStoryResponse != null) {
                storyChapterAdapter.setChapters(detailStoryResponse.getChapters());
            }
        });
    }

    private void customSearchView() {
        SearchView searchView = binding.svChapter;

        // Tùy chỉnh khung tìm kiếm bên trong SearchView
        try {
            // Tùy chỉnh mSearchPlate
            Field searchPlateField = SearchView.class.getDeclaredField("mSearchPlate");
            searchPlateField.setAccessible(true);
            View searchPlate = (View) searchPlateField.get(searchView);
            searchPlate.setBackgroundResource(R.drawable.edt_remove_line);

            // Tùy chỉnh EditText bên trong SearchView
            Field searchSrcTextViewField = SearchView.class.getDeclaredField("mSearchSrcTextView");
            searchSrcTextViewField.setAccessible(true);
            EditText searchEditText = (EditText) searchSrcTextViewField.get(searchView);
            searchEditText.setTextSize(12);
            searchEditText.setHintTextColor(Color.GRAY); // Đặt màu cho hint text
            searchEditText.setTextColor(Color.BLACK); // Đặt màu cho văn bản

            // Tùy chỉnh nút "x" bên trong SearchView
            Field closeButtonField = SearchView.class.getDeclaredField("mCloseButton");
            closeButtonField.setAccessible(true);
            ImageView closeButton = (ImageView) closeButtonField.get(searchView);
            closeButton.setImageResource(R.drawable.ic_exit);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        };
    }

}