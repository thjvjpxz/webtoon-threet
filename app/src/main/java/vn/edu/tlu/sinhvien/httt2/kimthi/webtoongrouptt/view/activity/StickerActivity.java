package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityStickerBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.Stickers;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.Utils;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StickerAdapter;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter.StickerDialogAdapter;

public class StickerActivity extends AppCompatActivity {
    private ActivityStickerBinding binding;
    private List<Stickers> stickersList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stickersList = Utils.readJson(this);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setAdapter(new StickerAdapter(stickersList, new StickerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Stickers stickers) {
                showDialog(stickers);
            }
        }));

        handleBack();
    }

    private void handleBack() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void showDialog(Stickers stickers) {
        Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_sticker);

        RecyclerView recyclerView = dialog.findViewById(R.id.rvSticker);
        StickerDialogAdapter adapter = new StickerDialogAdapter(stickers.getUrls());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        recyclerView.setAdapter(adapter);

        dialog.show();
    }
}