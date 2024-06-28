package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import static vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment.LevelFragment.getGradientDrawable;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.CategoryActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.DetailActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.InfoAppActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SearchActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.StickerActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.LevelActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentUserBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.CommentActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SearchMangaActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SecurityActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.UserViewModel;

public class UserFragment extends Fragment {
    FragmentUserBinding binding;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    SharedPrefManager sharedPrefManager;
    UserViewModel userViewModel;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);

        //Setup google sign out
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(requireContext(), gso);
        userViewModel = new UserViewModel(requireActivity().getApplication());
        sharedPrefManager = new SharedPrefManager(getContext());

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == LevelActivity.RESULT_OK) {
                observe();
            }
        });

        String avatarUrl = sharedPrefManager.getAvatar();
        Glide.with(this).load(avatarUrl).circleCrop().into(binding.userImage);

        binding.tvNameUser.setText(sharedPrefManager.getName());

        handleUserInterfaceActions();
        processInfoUser(resultLauncher);
        observe();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
        String avatar = sharedPrefManager.getAvatar();
        String name = sharedPrefManager.getName();
        Glide.with(this)
                .load(avatar)
                .circleCrop()
                .into(binding.userImage);
        binding.tvNameUser.setText(name);
    }

    private void processInfoUser(ActivityResultLauncher<Intent> resultLauncher) {
        binding.btnInfoUser.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SecurityActivity.class);
            resultLauncher.launch(intent);
        });
    }
    private void observe() {
        userViewModel.fetchUserData().observe(getViewLifecycleOwner(), userResponse -> {
            if (userResponse != null) {
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
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUserInterfaceActions () {
        binding.btnBookmark.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CategoryActivity.class);
            intent.putExtra("categoryId", String.valueOf(83));
            intent.putExtra("page", 1);
            startActivity(intent);
        });

        binding.btnRanking.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            intent.putExtra("type_ranking", "2");
            startActivity(intent);
        });

        binding.btnInfoApp.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), InfoAppActivity.class);
            startActivity(intent);
        });

        binding.btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Webtoon Group TT");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://truyenhdc.com");
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ ứng dụng"));
        });

        binding.btnStickerUser.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StickerActivity.class);
            startActivity(intent);
        });

        binding.btnCommentUser.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CommentActivity.class);
            startActivity(intent);
        });

        binding.btnLevelUser.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LevelActivity.class);
            startActivity(intent);
        });

        binding.btnSearchManga.setOnClickListener(v -> {
            CustomTabColorSchemeParams.Builder colorBuilder = new CustomTabColorSchemeParams.Builder();
            colorBuilder.setToolbarColor(ContextCompat.getColor(v.getContext(), R.color.primary_color));
            CustomTabColorSchemeParams colorParams = colorBuilder.build();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setDefaultColorSchemeParams(colorParams);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(v.getContext(), Uri.parse("https://truyenhdc.com/truyen-tranh/tim-truyen"));
        });

        binding.btnLogout.setOnClickListener(v -> {
            sharedPrefManager.removeToken();

            gsc.signOut();
            userViewModel.logOut().observe(getViewLifecycleOwner(), responseBody -> {
                if (responseBody != null) {
                    Intent intent = new Intent(getContext(), SignActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Đăng xuất thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}