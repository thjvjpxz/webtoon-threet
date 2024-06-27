package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class Constants {
    public static final int TYPE_WEBTOON_STORY = 1;
    public static final int TYPE_WEBTOON_COMIC = 0;
    public static final int TYPE_HISTORY_STORY = 0;
    public static final int TYPE_FAVOURITE_STORY = 1;
    public static final RequestOptions ARGB_8888 =
            new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
    public static final RequestOptions IMG_CENTER_CROP_ROUND_30_OPTIONS =
            new RequestOptions().transform(new CenterCrop(), new RoundedCorners(30));
}
