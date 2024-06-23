package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentStoryHomeBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.OnScrollChangeListener;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;

public class StoryHomeFragment extends Fragment {

    FragmentStoryHomeBinding binding;
    OnScrollChangeListener scrollChangeListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnScrollChangeListener) {
            scrollChangeListener = (OnScrollChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnScrollChangeListener");
        }
    }

    public StoryHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStoryHomeBinding.inflate(inflater, container, false);

        handleClickBtnStory();
        setProfile();

        binding.scrollView2.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = binding.scrollView2.getScrollY();
            int oldScrollY = binding.scrollView2.getTag() != null ?
                    (int) binding.scrollView2.getTag() : 0;
            if (scrollY > oldScrollY) {
                // Người dùng đang cuộn lên
                if (scrollChangeListener != null) {
                    scrollChangeListener.onScrollUp();
                }
            } else if (scrollY < oldScrollY) {
                // Người dùng đang cuộn xuống
                if (scrollChangeListener != null) {
                    scrollChangeListener.onScrollDown();
                }
            }
            binding.scrollView2.setTag(scrollY);
        });

        return binding.getRoot();
    }

    private void handleClickBtnStory() {
        binding.header.btnStory.setText("Truyện tranh");
        binding.header.btnStory.setOnClickListener(v -> {
            SharedPrefManager share = SharedPrefManager.getInstance(getContext());
            share.saveTypeWebtoon(Constants.TYPE_WEBTOON_COMIC);
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);

            getActivity().finish();
        });
    }

    private void setProfile() {
        SharedPrefManager share = SharedPrefManager.getInstance(getContext());
        if (share.isLoggedIn()) {
            binding.header.tvName.setText(share.getName());
            Glide.with(getContext())
                    .applyDefaultRequestOptions(Constants.ARGB_8888)
                    .load(share.getAvatar())
                    .into(binding.header.imgAvatar);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        scrollChangeListener = null;
    }
}