package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityCategoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityListTypeComicBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.ComicByCategoryAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ComicByCategoryViewModel;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ListTypeComicViewModel;

public class ListTypeComicActivity extends AppCompatActivity {

    ActivityListTypeComicBinding binding;

    ListTypeComicViewModel listTypeComicViewModel;
    private int page = 1;
    private int lastPage = 1;
    private boolean isLoading = false;
    private ComicByCategoryAdapter adapter;
    private List<Comic> comicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityListTypeComicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setColorFilter(getResources().getColor(R.color.black));
        binding.ivBack.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        String slug = intent.getStringExtra("slug");
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        if(slug != null) {
            listTypeComicViewModel = new ListTypeComicViewModel(slug, page);
            observe();
        }
        initScrollListener();
    }

    private void observe() {
        listTypeComicViewModel.getIsLoaded().observe(this, isLoaded -> {
            if (isLoaded) {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                isLoading = false;
            }else{
                binding.shimmerViewContainer.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        listTypeComicViewModel.getResponse().observe(this, comicByCategoryResponse -> {
            if (comicByCategoryResponse != null) {
                binding.tvCategory.setText(comicByCategoryResponse.getTitle());
                lastPage = comicByCategoryResponse.getComics().getLast_page();
                if (page > 1) {
                    comicList.remove(comicList.size() - 1);
                    adapter.notifyItemRemoved(comicList.size());
                }
                List<Comic> newComics = comicByCategoryResponse.getComics().getData();
                comicList.addAll(newComics);
                if (adapter == null) {
                    adapter = new ComicByCategoryAdapter(comicList);
                    binding.rvCategory.setAdapter(adapter);
                } else {
                    adapter.notifyItemRangeInserted(comicList.size() - newComics.size(), newComics.size());
                }
                isLoading = false;
            }
        });
    }

    private void initScrollListener() {
        binding.main.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (!isLoading && page < lastPage) {
                        comicList.add(null);
                        adapter.notifyItemInserted(comicList.size() - 1);
                        page++;
                        listTypeComicViewModel.loadMoreData();
                        isLoading = true;
                    }
                }
            }
        });
    }
}