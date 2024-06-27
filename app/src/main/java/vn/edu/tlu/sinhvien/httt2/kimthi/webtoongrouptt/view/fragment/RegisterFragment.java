package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentRegisterBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.SignViewModel;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private SignViewModel signViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        signViewModel = new ViewModelProvider(this).get(SignViewModel.class);

        binding.btnRegister.setOnClickListener(v -> {
            String name = binding.edName.getText().toString();
            String email = binding.edEmail.getText().toString();
            String password = binding.edPassword.getText().toString();
            String confirmPassword = binding.edConfirmPassword.getText().toString();
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin",
                        Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                RegisterRequest registerRequest = new RegisterRequest(name, email, password);
                signViewModel.registerUser(registerRequest).observe(getViewLifecycleOwner(),
                        responseBody -> {
                            if (responseBody != null) {
                                Toast.makeText(getContext(), "Đăng ký thành công",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), SignActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Đăng ký thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return binding.getRoot();
    }
}