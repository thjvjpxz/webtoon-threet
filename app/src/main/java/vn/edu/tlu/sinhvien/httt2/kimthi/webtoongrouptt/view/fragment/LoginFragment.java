package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import retrofit2.Call;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentLoginBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = "Chưa làm xong nhấn cc";

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignActivity) requireActivity()).switchToForgotPassword();
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
            LoginRequest loginRequest = new LoginRequest(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString());
            ApiService apiService = ApiClient.getRetrofit(getContext()).create(ApiService.class);
            Call<LoginResponse> call = apiService.loginUser(loginRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        assert loginResponse != null;
                        SharedPrefManager.getInstance(getContext()).saveToken(loginResponse.getToken());
                        SharedPrefManager.getInstance(getContext()).saveAvatar(loginResponse.getAvatar());
                        SharedPrefManager.getInstance(getContext()).saveName(loginResponse.getName());
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.loginFacebook.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        binding.loginGoogle.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        binding.loginTwitter.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

}