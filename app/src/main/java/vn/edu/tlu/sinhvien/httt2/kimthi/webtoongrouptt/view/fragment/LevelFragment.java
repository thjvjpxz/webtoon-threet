package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentLevelBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.User;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.LevelActivity;

public class LevelFragment extends Fragment {

    private FragmentLevelBinding binding;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLevelBinding.inflate(inflater, container, false);
        LevelActivity levelActivity = (LevelActivity) getActivity();

        assert levelActivity != null;
        ViewPager2 viewPager2 = levelActivity.findViewById(R.id.viewPager);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.getRoot().post(new Runnable() {
                    @Override
                    public void run() {
                        updateContent(position);
                    }
                });
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                binding.getRoot().post(new Runnable() {
                    @Override
                    public void run() {
                        updateContent(position);
                    }
                });
            }

            private void updateContent(int position) {
                if (getArguments() != null) {
                    if (position == 0 && user.getLevel() != null) {
                        GradientDrawable originalDrawable = (GradientDrawable) binding.tvLevel.getBackground().mutate();
                        GradientDrawable newDrawable = getGradientDrawable(originalDrawable);

                        int color = Color.parseColor(user.getLevel().getStyle());
                        newDrawable.setStroke(2, color);
                        binding.tvLevel.setBackground(newDrawable);
                        binding.tvLevel.setTextColor(color);
                        binding.tvLevel.setText(user.getLevel().getLevel());
                        binding.tvText3D.setBoldText(true);
                        binding.tvText3D.setText(user.getName());
                        binding.tvText3D.setGifFromUrl(user.getLevel().getImage());
                    } else if (position == 1 && user.getNextLevel() != null) {
                        GradientDrawable originalDrawable = (GradientDrawable) binding.tvLevel.getBackground().mutate();
                        GradientDrawable newDrawable = getGradientDrawable(originalDrawable);

                        int color = Color.parseColor(user.getNextLevel().getStyle());
                        newDrawable.setStroke(2, color);
                        binding.tvLevel.setBackground(newDrawable);
                        binding.tvLevel.setTextColor(color);
                        binding.tvLevel.setText(user.getNextLevel().getLevel());
                        if (user.getNextLevel().getId() == 2) {
                            binding.tvText3D.setImageFromUrl(user.getNextLevel().getImage());
                        } else {
                            binding.tvText3D.setGifFromUrl(user.getNextLevel().getImage());
                        }
                    } else {
                        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_pink_radius, null);
                        binding.tvLevel.setBackground(drawable);
                        binding.tvLevel.setText("Sơ cấp");
                        binding.tvLevel.setTextColor(Color.parseColor("#F97892"));
                        binding.tvText3D.setText(user.getName());
                        binding.tvText3D.setBoldText(true);
                        binding.tvText3D.setImageFromUrl("https://truyenhdc.com/images/1.jpg");
                    }
                }
            }
        });

        return binding.getRoot();
    }

    @NonNull
    public static GradientDrawable getGradientDrawable(GradientDrawable originalDrawable) {
        GradientDrawable newDrawable = new GradientDrawable();
        newDrawable.setShape(originalDrawable.getShape());
        newDrawable.setSize(originalDrawable.getIntrinsicWidth(), originalDrawable.getIntrinsicHeight());
        newDrawable.setBounds(originalDrawable.getBounds());
        newDrawable.setGradientType(originalDrawable.getGradientType());
        newDrawable.setGradientCenter(originalDrawable.getGradientCenterX(), originalDrawable.getGradientCenterY());
        newDrawable.setGradientRadius(originalDrawable.getGradientRadius());
        newDrawable.setCornerRadius(originalDrawable.getCornerRadius());
        newDrawable.setColors(originalDrawable.getColors());
        return newDrawable;
    }
}
