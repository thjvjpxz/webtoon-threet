package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentSearchBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story.Story;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.repository.FilterComicRepository;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.GridSpacingItemDecoration;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.OnItemSearchClickListener;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.CategoryActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailStoryActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SearchActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.SearchFragmentAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.FilterComicViewModel;

public class SearchFragment extends Fragment {
    private static final String ARG_TAB_POSITION = "tab_position";
    private int tabPosition;
    private  String query = "";
    private  String sort = "";
    private int type;
    private FragmentSearchBinding binding;
    private FilterComicViewModel filterComicViewModel;

    public static SearchFragment newInstance(int tabPosition, String query, String sort, int type) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_POSITION, tabPosition);
        args.putString("query", query);
        args.putString("sort", sort);
        args.putInt("type", type);
        Log.d("SearchFragment", "newInstance: " + sort + " " + type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterComicViewModel = new FilterComicViewModel(requireActivity().getApplication());
        if (getArguments() != null) {
            tabPosition = getArguments().getInt(ARG_TAB_POSITION);
            if (tabPosition == 2) {
                tabPosition = 0;
            } else if (tabPosition == 0) {
                tabPosition = 2;
            }
        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            query = bundle.getString("query");
            sort = bundle.getString("sort");
            type = bundle.getInt("type");
        }

        observe(Integer.parseInt(sort), tabPosition, query);
        return binding.getRoot();
    }

    public void updateData(String query, String sort, int type) {
        this.query = query;
        this.sort = sort;
        this.type = type;
    }

    public void updateDataRvView(String query, String sort) {
        this.query = query;
        this.sort = sort;
        observe(Integer.parseInt(sort), tabPosition, query);
    }

    private void observe (int sort, int status, String keyword) {
        if (type == 0) {
            filterComicViewModel.fetchDataComics(1, sort, tabPosition, query).observe(getViewLifecycleOwner(), dataResponse -> {
                if (dataResponse != null) {
                    binding.rvSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    SearchFragmentAdapter adapter = new SearchFragmentAdapter(dataResponse.getComics().getData(), type, new OnItemSearchClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(Comic comic) {
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            intent.putExtra("COMIC_ID", comic.getId().toString());
                            startActivity(intent);
                        }
                    });
                    binding.rvSearch.setAdapter(adapter);

                    for (int i = 0; i < binding.rvSearch.getItemDecorationCount(); i++) {
                        binding.rvSearch.removeItemDecorationAt(i);
                    }

                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
                    binding.rvSearch.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));
                }
            });
        } else if (type == 1) {
            filterComicViewModel.fetchDataStories(1, query).observe(getViewLifecycleOwner(), dataResponse -> {
                if (dataResponse != null) {
                    binding.rvSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    SearchFragmentAdapter adapter = new SearchFragmentAdapter(dataResponse.getStories().getData(), type, new OnItemSearchClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(Comic comic) {
//                            Intent intent = new Intent(getContext(), DetailStoryActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("story", story);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
                        }
                    });
                    binding.rvSearch.setAdapter(adapter);

                    for (int i = 0; i < binding.rvSearch.getItemDecorationCount(); i++) {
                        binding.rvSearch.removeItemDecorationAt(i);
                    }

                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
                    binding.rvSearch.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));
                }
            });
        }
    }
}
