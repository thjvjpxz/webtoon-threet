package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.FragmentTabAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.ForgotFragment; // Assuming ForgotFragment exists
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivitySignBinding;

public class SignActivity extends AppCompatActivity {

    ActivitySignBinding binding;
    FragmentTabAdapter fragmentTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        if(sharedPrefManager.getToken() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        fragmentTabAdapter = new FragmentTabAdapter(getSupportFragmentManager(), getLifecycle());
        binding.tabLayout.removeAllTabs();
        binding.tabLayout.setVisibility(View.VISIBLE);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đăng Nhập"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đăng Ký"));

        binding.viewPager2.setAdapter(fragmentTabAdapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null) {
                    binding.viewPager2.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

    }

    public void switchToForgotPassword() {

        binding.tabLayout.removeAllTabs();
        binding.tabLayout.setVisibility(View.GONE);

        FragmentTabAdapter newAdapter = new FragmentTabAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new ForgotFragment();
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        };

        binding.viewPager2.setAdapter(newAdapter);
    }
}