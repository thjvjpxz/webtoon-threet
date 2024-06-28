package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ActivityLoadwebBinding;

public class LoadViewActivity extends AppCompatActivity {
    private ActivityLoadwebBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoadwebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CustomTabColorSchemeParams.Builder colorBuilder = new CustomTabColorSchemeParams.Builder();
        colorBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.primary_color));
        CustomTabColorSchemeParams colorParams = colorBuilder.build();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setDefaultColorSchemeParams(colorParams);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://www.facebook.com"));
    }
}