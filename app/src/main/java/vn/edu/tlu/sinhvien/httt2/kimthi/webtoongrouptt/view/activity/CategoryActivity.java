package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityCategoryBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.ComicByCategoryAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.ComicByCategoryViewModel;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;
    ComicByCategoryViewModel comicByCategoryViewModel;
    private int page = 1;
    private int lastPage = 1;
    private boolean isLoading = false;
    private ComicByCategoryAdapter adapter;
    private List<Comic> comicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setColorFilter(getResources().getColor(R.color.black));
        binding.ivBack.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("categoryId");
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        if(categoryId != null) {
            comicByCategoryViewModel = new ComicByCategoryViewModel(categoryId, page);
            observe();
        }
        initScrollListener();
    }

    private void observe() {
        comicByCategoryViewModel.getIsLoaded().observe(this, isLoaded -> {
            if (isLoaded) {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                isLoading = false;
            }else{
                binding.shimmerViewContainer.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        comicByCategoryViewModel.getResponseComicByCategory().observe(this, comicByCategoryResponse -> {
            if (comicByCategoryResponse != null) {
                binding.tvCategory.setText("Thể loại " + comicByCategoryResponse.getCategory().getName());
                lastPage = comicByCategoryResponse.getData().getLast_page();
                if (page > 1) {
                    comicList.remove(comicList.size() - 1);
                    adapter.notifyItemRemoved(comicList.size());
                }
                List<Comic> newComics = comicByCategoryResponse.getData().getData();
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
                        comicByCategoryViewModel.loadMoreData();
                        isLoading = true;
                    }
                }
            }
        });
    }
}