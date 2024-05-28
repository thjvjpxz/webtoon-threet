package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.ResponseBody;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentRegisterBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.RegisterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

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

        String message = "Chưa làm xong nhấn cc";

        binding.btnRegister.setOnClickListener(v -> {
            EditText name = binding.edName;
            EditText email = binding.edEmail;
            EditText password = binding.edPassword;
            EditText confirmPassword = binding.edConfirmPassword;
            if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                RegisterRequest registerRequest = new RegisterRequest(name.getText().toString(), email.getText().toString(), password.getText().toString());
                ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
                Call<ResponseBody> call = apiService.registerUser(registerRequest);
                call.enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            password.setText("");
                            confirmPassword.setText("");
                            Intent intent = new Intent(getContext(), SignActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
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