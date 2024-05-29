package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.SharedPrefManager.SharedPrefManager;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentLoginBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.LoginRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.GoogleResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.response.LoginResponse;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiClient;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.network.ApiService;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


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

        //Setup google sign in
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(requireContext(), gso);

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
            apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        assert loginResponse != null;
                        SharedPrefManager share = SharedPrefManager.getInstance(getContext());
                        share.saveToken(loginResponse.getToken());
                        share.saveAvatar(loginResponse.getAvatar());
                        share.saveName(loginResponse.getName());
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Thông báo")
                                .setMessage("Đăng nhập thất bại")
                                .setPositiveButton("OK", (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Thông báo")
                            .setMessage("Đăng nhập thất bại")
                            .setPositiveButton("OK", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                }
            });
        });

        binding.loginFacebook.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        binding.imvGoogle.setOnClickListener(v -> {
            signIn();
        });

        binding.loginTwitter.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                            .addOnSuccessListener(googleSignInAccount -> {
                                Uri url = googleSignInAccount.getPhotoUrl();
                                if (url == null) {
                                    url = Uri.parse("https://webdoctor.vn/wp-content/uploads/2018/02/Cach-dang-xuat-de-thay-doi-tai-khoan-google-mac-dinh-tren-android-03.jpg");
                                }
                                GoogleRequest googleRequest = new GoogleRequest(googleSignInAccount.getDisplayName(), googleSignInAccount.getEmail(), url.toString());
                                ApiService apiService = ApiClient.getRetrofit(getContext()).create(ApiService.class);
                                Call<GoogleResponse> call = apiService.loginGoogle(googleRequest);
                                call.enqueue(new Callback<GoogleResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<GoogleResponse> call, @NonNull Response<GoogleResponse> response) {
                                        if (response.isSuccessful()) {
                                            GoogleResponse googleResponse = response.body();
                                            assert googleResponse != null;
                                            SharedPrefManager.getInstance(getContext()).saveToken(googleResponse.getToken());
                                            SharedPrefManager.getInstance(getContext()).saveAvatar(googleResponse.getAvatar());
                                            SharedPrefManager.getInstance(getContext()).saveName(googleResponse.getName());
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            new AlertDialog.Builder(getContext())
                                                    .setTitle("Thông báo")
                                                    .setMessage("Đăng nhập thất bại")
                                                    .setPositiveButton("OK", (dialog, which) -> {
                                                        dialog.dismiss();
                                                    })
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<GoogleResponse> call, @NonNull Throwable t) {
                                        new AlertDialog.Builder(getContext())
                                                .setTitle("Thông báo")
                                                .setMessage("Đăng nhập thất bại")
                                                .setPositiveButton("OK", (dialog, which) -> {
                                                    dialog.dismiss();
                                                })
                                                .show();
                                    }
                                });
                            })
                            .addOnFailureListener(e -> {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Thông báo")
                                        .setMessage("Đăng nhập thất bại")
                                        .setPositiveButton("OK", (dialog, which) -> {
                                            dialog.dismiss();
                                        })
                                        .show();
                            });
                }
            }
    );

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }
}