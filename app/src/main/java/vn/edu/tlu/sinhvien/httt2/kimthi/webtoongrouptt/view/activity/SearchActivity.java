package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivitySearchBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.SearchPagerAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private String sort = "0";

    public String getQuery() {
        return binding.searchView.getQuery().toString();
    }

    public String getSort() {
        return sort;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Tất cả"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Hoàn thành"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đang ra"));

        SearchPagerAdapter searchPagerAdapter = new SearchPagerAdapter(this);
        binding.viewPager2.setAdapter(searchPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Tất cả");
                        break;
                    case 1:
                        tab.setText("Hoàn thành");
                        break;
                    case 2:
                        tab.setText("Đang ra");
                        break;
                }
            }
        }).attach();

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + position);
                if (fragment instanceof SearchFragment) {
                    ((SearchFragment) fragment).updateData(getQuery(), getSort());
                }
            }
        });

        binding.actionBarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.actionBarSpinner.setSelection(position);
                sort = String.valueOf(position);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + binding.viewPager2.getCurrentItem());
                if (fragment instanceof SearchFragment) {
                    ((SearchFragment) fragment).updateDataRvView(getQuery(), getSort());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}