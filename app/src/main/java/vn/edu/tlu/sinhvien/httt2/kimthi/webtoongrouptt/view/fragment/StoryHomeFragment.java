package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentStoryHomeBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util.Constants;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;

public class StoryHomeFragment extends Fragment {

    FragmentStoryHomeBinding binding;

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
}