package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentStoryHomeBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.TypeStoryConst;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.OnScrollChangeListener;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.StoriesByTypeActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SearchActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StoryHomeAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.StoryHomeViewModel;

public class StoryHomeFragment extends Fragment {

    private FragmentStoryHomeBinding binding;
    private OnScrollChangeListener scrollChangeListener;
    private StoryHomeViewModel viewModel;
    private StoryHomeAdapter adapter;
    private Handler sliderHandler = new Handler();

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.vpHot.setCurrentItem(binding.vpHot.getCurrentItem() + 1);
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnScrollChangeListener) {
            scrollChangeListener = (OnScrollChangeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement " +
                    "OnScrollChangeListener");
        }
    }

    public StoryHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStoryHomeBinding.inflate(inflater, container, false);

        handleClickBtnStory();
        setProfile();

        viewModel = new ViewModelProvider(this).get(StoryHomeViewModel.class);

        binding.scrollView2.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = binding.scrollView2.getScrollY();
            int oldScrollY = binding.scrollView2.getTag() != null ?
                    (int) binding.scrollView2.getTag() : 0;
            if (scrollY > oldScrollY) {
                // Người dùng đang cuộn lên
                if (scrollChangeListener != null) {
                    scrollChangeListener.onScrollUp();
                }
            } else if (scrollY < oldScrollY) {
                // Người dùng đang cuộn xuống
                if (scrollChangeListener != null) {
                    scrollChangeListener.onScrollDown();
                }
            }
            binding.scrollView2.setTag(scrollY);
        });

        handleLoading();
        handleStories();
        handleClickWatchMore();
        binding.header.btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            intent.putExtra("type", Constants.TYPE_STORIES);
            startActivity(intent);
        });


        return binding.getRoot();
    }

    private void handleClickWatchMore() {
        binding.btnHotAdd.setOnClickListener(v -> initIntent(TypeStoryConst.TYPE_HOT));
        binding.btnCompletedAdd.setOnClickListener(v -> initIntent(TypeStoryConst.TYPE_COMPLETED));
        binding.btnConvertAdd.setOnClickListener(v -> initIntent(TypeStoryConst.TYPE_CONVERT));
        binding.btnTranslationAdd.setOnClickListener(v -> initIntent(TypeStoryConst.TYPE_TRANSLATION));
    }

    private void initIntent(int type) {
        Intent intent = new Intent(getContext(), StoriesByTypeActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }


    private void handleLoading() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.pbHot.setVisibility(View.GONE);
                binding.pbCompleted.setVisibility(View.GONE);
                binding.pbTranslation.setVisibility(View.GONE);
                binding.pbConvert.setVisibility(View.GONE);
            } else {
                binding.pbHot.setVisibility(View.VISIBLE);
                binding.pbCompleted.setVisibility(View.VISIBLE);
                binding.pbTranslation.setVisibility(View.VISIBLE);
                binding.pbConvert.setVisibility(View.VISIBLE);
            }
        });
    }

    public void handleHot(List<Story> stories) {
        adapter = new StoryHomeAdapter(stories, binding.vpHot, TypeStoryConst.TYPE_HOT);
        binding.vpHot.setAdapter(adapter);
        binding.vpHot.setClipToPadding(false);
        binding.vpHot.setClipChildren(false);
        binding.vpHot.setOffscreenPageLimit(3);
        binding.vpHot.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        binding.vpHot.setPageTransformer(compositePageTransformer);
        binding.vpHot.setCurrentItem(1);
        binding.vpHot.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    public void handleStories() {
        viewModel.fetchStoryHomeData().observe(getViewLifecycleOwner(), storyHomeResponse -> {
            if (storyHomeResponse != null) {
                handleHot(storyHomeResponse.getStoriesHot());
                handleStoriesHori(
                        storyHomeResponse.getStoriesComplete(),
                        storyHomeResponse.getStoriesConvert(),
                        storyHomeResponse.getStoriesTranslation()
                );
            }
        });
    }

    private void handleStoriesHori(List<Story> storiesCompleted, List<Story> storiesConvert,
                                   List<Story> storiesTranslation) {
        // get 6 stories completed
        if (storiesCompleted.size() > 6) {
            storiesCompleted = storiesCompleted.subList(0, 6);
        }
        adapter = new StoryHomeAdapter(storiesCompleted, TypeStoryConst.TYPE_COMPLETED);
        binding.rvCompleted.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvCompleted.setAdapter(adapter);

        adapter = new StoryHomeAdapter(storiesConvert, TypeStoryConst.TYPE_CONVERT);
        binding.rvConvert.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.rvConvert.setAdapter(adapter);

        if (storiesTranslation.size() > 6) {
            storiesTranslation = storiesTranslation.subList(0, 6);
        }
        adapter = new StoryHomeAdapter(storiesTranslation, TypeStoryConst.TYPE_TRANSLATION);
        binding.rvTranslation.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvTranslation.setAdapter(adapter);
    }

    private void handleClickBtnStory() {
        binding.header.btnStory.setText("Truyện tranh");
        binding.header.btnStory.setOnClickListener(v -> {
            SharedPrefManager share = SharedPrefManager.getInstance(getContext());
            share.saveTypeWebtoon(Constants.TYPE_WEBTOON_COMIC);
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }

    private void setProfile() {
        SharedPrefManager share = SharedPrefManager.getInstance(getContext());
        if (share.isLoggedIn()) {
            binding.header.tvName.setText(share.getName());
            Glide.with(getContext()).applyDefaultRequestOptions(Constants.ARGB_8888).load(share.getAvatar()).into(binding.header.imgAvatar);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        scrollChangeListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }
}