package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityMainBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.NavigationBarAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavigationBarAdapter navigationBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            startActivity(new Intent(this, SignActivity.class));
            finish();
        }

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        navigationBarAdapter = new NavigationBarAdapter(getSupportFragmentManager(), getLifecycle(), sharedPrefManager.getTypeWebtoon());

        binding.vpMain.setAdapter(navigationBarAdapter);

        Log.d("MainActivity", "active");

        binding.bottomNavigationBar.setItemSelected(R.id.home, true);

        binding.bottomNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.home) {
                    binding.vpMain.setCurrentItem(0);
                } else if (i == R.id.compass) {
                    binding.vpMain.setCurrentItem(2);
                } else {
                    binding.vpMain.setCurrentItem(1);
                }
            }
        });

        binding.vpMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
               super.onPageSelected(position);
            }
        });
    }
}