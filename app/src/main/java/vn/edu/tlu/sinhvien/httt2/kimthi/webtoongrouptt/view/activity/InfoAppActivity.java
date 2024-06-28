package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityInfoAppBinding;

public class InfoAppActivity extends AppCompatActivity {
    private ActivityInfoAppBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleLoadWeb();
    }

    private void handleLoadWeb () {
        binding.ivLoadFacebook.setOnClickListener(v -> {
            CustomTabColorSchemeParams.Builder colorBuilder = new CustomTabColorSchemeParams.Builder();
            colorBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.primary_color));
            CustomTabColorSchemeParams colorParams = colorBuilder.build();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setDefaultColorSchemeParams(colorParams);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse("https://www.facebook.com/quangthangno1"));
        });

        binding.ivSendGmail.setOnClickListener(v -> {
            String email = binding.tvEmail.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SENDTO);

            intent.setData(Uri.parse("mailto:" + email));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No email client available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivLoadInstagram.setOnClickListener(v -> {
            CustomTabColorSchemeParams.Builder colorBuilder = new CustomTabColorSchemeParams.Builder();
            colorBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.primary_color));
            CustomTabColorSchemeParams colorParams = colorBuilder.build();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setDefaultColorSchemeParams(colorParams);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse("https://www.instagram.com/thangvc03/"));
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }
}