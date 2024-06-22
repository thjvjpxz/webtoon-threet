package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;

public class MyPreloadModelProvider implements ListPreloader.PreloadModelProvider<String> {
    private List<Image> images;
    private Context context;

    public MyPreloadModelProvider(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public List<String> getPreloadItems(int position) {
        Image image = images.get(position);
        return Collections.singletonList(image.getLink());
    }

    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(String url) {
        return Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }
}
