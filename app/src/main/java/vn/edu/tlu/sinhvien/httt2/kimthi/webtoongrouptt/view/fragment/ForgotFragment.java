package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentForgotBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.ForgotRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.ForgotResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.SignViewModel;

public class ForgotFragment extends Fragment {
    private FragmentForgotBinding binding;
    private SignViewModel signViewModel;

    public ForgotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotBinding.inflate(inflater, container, false);
        signViewModel = new ViewModelProvider(this).get(SignViewModel.class);

        binding.btnLoginNow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SignActivity.class);
            startActivity(intent);
        });

        binding.btnForgot.setOnClickListener(v -> {
            ForgotRequest forgotRequest = new ForgotRequest(binding.edtEmail.getText().toString());
            signViewModel.forgotPassword(forgotRequest).observe(getViewLifecycleOwner(),
                    forgotResponse -> {
                if (forgotResponse != null) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Thành công")
                            .setMessage("Email đã được gửi. Vui lòng kiểm tra Email của bạn!")
                            .setPositiveButton(android.R.string.yes,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Thông báo")
                            .setMessage("Email không tồn tại")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            });
            binding.edtEmail.setText("");
        });
        return binding.getRoot();
    }
}