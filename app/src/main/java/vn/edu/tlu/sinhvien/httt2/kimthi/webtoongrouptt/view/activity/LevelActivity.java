package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import static vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.LevelFragment.getGradientDrawable;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.bumptech.glide.Glide;

import java.util.Objects;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityLevelBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.LevelAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.LevelPagerAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.UserViewModel;

public class LevelActivity extends AppCompatActivity {
    ActivityLevelBinding binding;
    private UserViewModel userViewModel;
    private final boolean fromUser = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        binding = ActivityLevelBinding.inflate(getLayoutInflater());
        userViewModel = new UserViewModel(getApplication());
        setContentView(binding.getRoot());

        processBack();
        observe();
    }

    private void observe() {
        userViewModel.fetchUserData().observe(this, userResponse -> {
            if (userResponse != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                binding.rvLevel.setLayoutManager(layoutManager);

                Bundle bundle = new Bundle();
                bundle.putSerializable("user", userResponse.getUser());
                binding.viewPager.setAdapter(new LevelPagerAdapter(this, bundle));
                LevelAdapter levelAdapter = new LevelAdapter(this, userResponse.getUser());

                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(binding.rvLevel);
                binding.rvLevel.setAdapter(levelAdapter);

                binding.rvLevel.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            int position = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                            if (fromUser) {
                                binding.viewPager.setCurrentItem(position, true);
                            }
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (!fromUser) {
                            int position = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                            binding.viewPager.setCurrentItem(position, true);
                        }
                    }
                });

                Glide.with(this).load(userResponse.getUser().getAvatar()).circleCrop().into(binding.userImage);
                binding.tvNameUser.setText(userResponse.getUser().getName());
                if (userResponse.getUser().getLevel() != null) {
                    binding.tvLevelUser.setText(userResponse.getUser().getLevel().getLevel());
                    GradientDrawable originalDrawable = (GradientDrawable) binding.tvLevelUser.getBackground().mutate();
                    GradientDrawable newDrawable = getGradientDrawable(originalDrawable);
                    int color = Color.parseColor(userResponse.getUser().getLevel().getStyle());
                    newDrawable.setStroke(2, color);
                    binding.tvLevelUser.setTextColor(color);
                    binding.tvLevelUser.setBackground(newDrawable);
                } else {
                    binding.tvLevelUser.setText("Sơ cấp");
                }
            } else {
                Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processBack() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }
}