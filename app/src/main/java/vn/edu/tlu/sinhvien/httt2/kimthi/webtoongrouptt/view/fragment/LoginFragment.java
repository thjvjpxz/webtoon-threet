package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.TwitterActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.FragmentLoginBinding;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.SignActivity;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.SignViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private SignViewModel signViewModel;
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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(requireContext(), gso);
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        signViewModel = new ViewModelProvider(this).get(SignViewModel.class);

        processLoginUser();
        processForgotPassword();
        processLoginGoogle();
        processLoginTwitter();

        return binding.getRoot();
    }

    private void processLoginUser() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPassword.getText().toString();
            signViewModel.loginUser(email, password).observe(getViewLifecycleOwner(),
                    loginResponse -> {
                        if (loginResponse != null) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d("TOKEN", "null");
                            showAlertDialog("Thông báo", "Đăng nhập thất bại");
                        }
                    });

        });
    }

    private void processForgotPassword() {
        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignActivity) requireActivity()).switchToForgotPassword();
            }
        });

    }

    private void processLoginGoogle() {
        binding.imvGoogle.setOnClickListener(v -> {
            signIn();
        });
    }

    private void processLoginTwitter() {
        binding.loginTwitter.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TwitterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
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
                                    url = Uri.parse("https://webdoctor.vn/wp-content/uploads/2018" +
                                            "/02/Cach-dang-xuat-de-thay-doi-tai-khoan-google-mac" +
                                            "-dinh-tren-android-03.jpg");
                                }
                                GoogleRequest googleRequest =
                                        new GoogleRequest(
                                                googleSignInAccount.getDisplayName(),
                                                googleSignInAccount.getEmail(),
                                                url.toString());

                                signViewModel.loginGoogle(googleRequest).observe(getViewLifecycleOwner(), googleResponse -> {
                                    if (googleResponse != null) {
                                        Intent intent = new Intent(getContext(),
                                                MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        showAlertDialog("Thông báo", "Đăng nhập thất bại");
                                    }
                                });
                            })
                            .addOnFailureListener(e -> {
                                showAlertDialog("Thông báo", "Đăng nhập thất bại");
                            });
                }
            }
    );

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}