package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityMainBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.NavigationBarAdapter;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavigationBarAdapter navigationBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, SignActivity.class));
            finish();
        }

        navigationBarAdapter = new NavigationBarAdapter(getSupportFragmentManager(), getLifecycle());

        binding.vpMain.setAdapter(navigationBarAdapter);
        binding.vpMain.setUserInputEnabled(false);

        binding.bottomNavigationBar.setItemSelected(R.id.home, true);

        binding.bottomNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.home) {
                    binding.vpMain.setCurrentItem(0);
                } else if (i == R.id.heart) {
                    binding.vpMain.setCurrentItem(1);
                } else {
                    binding.vpMain.setCurrentItem(2);
                }
            }
        });

        binding.vpMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
               super.onPageSelected(position);
               switch (position) {
                   case 0:
                       binding.bottomNavigationBar.setItemSelected(R.id.home, true);
                       break;
                   case 1:
                       binding.bottomNavigationBar.setItemSelected(R.id.heart, true);
                       break;
                   case 2:
                       binding.bottomNavigationBar.setItemSelected(R.id.compass, true);
                       break;
               }
            }
        });
    }

    public void openDetailActivity(String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("COMIC_ID", id);
        startActivity(intent);
    }
}