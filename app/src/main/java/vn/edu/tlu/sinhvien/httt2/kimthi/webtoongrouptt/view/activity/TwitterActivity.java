package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.auth.UserInfo;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.GoogleRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Request.TwitterRequest;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.viewmodel.SignViewModel;

public class TwitterActivity extends SignActivity {
    private SignViewModel signViewModel;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_twitter);
        signViewModel = new ViewModelProvider(this).get(SignViewModel.class);
        loginWithTwitter();
    }

    private void loginWithTwitter() {
        firebaseAuth = FirebaseAuth.getInstance();
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        provider.addCustomParameter("lang", "en");
        provider.addCustomParameter("include_email", "true");

        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    handleSignInResult(authResult);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TwitterActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
        } else {
            firebaseAuth
                    .startActivityForSignInWithProvider(this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    handleSignInResult(authResult);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TwitterActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
        }
    }

    private void handleSignInResult(AuthResult authResult) {
        FirebaseUser user = authResult.getUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photoUrl = null;
            if (user.getPhotoUrl() != null) {
                photoUrl = user.getPhotoUrl().toString();
            }

            GoogleRequest twitterRequest = new GoogleRequest(name, email, photoUrl);
            signViewModel.loginGoogle(twitterRequest).observe(this, twitterResponse -> {
                if (twitterResponse != null) {
                    Intent intent = new Intent(TwitterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TwitterActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(TwitterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
