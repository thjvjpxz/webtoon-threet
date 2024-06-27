package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.DialogUserBinding;

public class UserDialogFragment extends DialogFragment {
    private DialogUserBinding binding;
    private OnConfirmListener onConfirmListener;
    String dialogType;

    public UserDialogFragment(String dialogType) {
        this.dialogType = dialogType;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogUserBinding.inflate(inflater, container, false);

        if (dialogType.equals("EmailDialog")) {
            binding.tvTitle.setText("Đổi Email");
            binding.edtNickname.setHint("Nhập Email bạn muốn đổi");
        } else if (dialogType.equals("NicknameDialog")) {
            binding.tvTitle.setText("Đặt biệt danh");
            binding.edtNickname.setHint("Nhập biệt danh bạn muốn đổi");
        }

        binding.btnDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = binding.edtNickname.getText().toString();
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm(nickname);
                }
                dismiss();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int marginPixels = (int) (20 * getResources().getDisplayMetrics().density);

            int screenWidth = getResources().getDisplayMetrics().widthPixels;

            int dialogWidth = screenWidth - 2 * marginPixels;

            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(dialogWidth, height);
            dialog.getWindow().setDimAmount(0.2f);
        }
    }

    public interface OnConfirmListener {
        void onConfirm(String nickname);
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }
}