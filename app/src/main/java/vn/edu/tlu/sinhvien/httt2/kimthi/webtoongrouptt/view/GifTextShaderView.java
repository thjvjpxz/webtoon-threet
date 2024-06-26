package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class GifTextShaderView extends AppCompatTextView {
    private Paint paint;
    private Shader shader;
    private GifDrawable gifDrawable;
    private Handler handler = new Handler();

    public GifTextShaderView(Context context) {
        super(context);
        init(context, null);
    }

    public GifTextShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GifTextShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = getPaint();
    }

    public void setGifUrl(String url) {
        loadGif(getContext(), url);
    }

    private void loadGif(Context context, String url) {
        Glide.with(context)
                .asGif()
                .load(url)
                .into(new CustomTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(GifDrawable resource, Transition<? super GifDrawable> transition) {
                        gifDrawable = resource;
                        gifDrawable.setLoopCount(GifDrawable.LOOP_FOREVER);
                        updateShader();
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        // Handle cleanup
                    }
                });
    }

    private void updateShader() {
        if (gifDrawable != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = gifDrawable.getFirstFrame();
                    shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                    paint.setShader(shader);
                    invalidate();

                    gifDrawable.start();
                    gifDrawable.setCallback(new Drawable.Callback() {
                        @Override
                        public void invalidateDrawable(Drawable who) {
                            invalidate();
                        }

                        @Override
                        public void scheduleDrawable(Drawable who, Runnable what, long when) {
                            handler.postAtTime(what, when);
                        }

                        @Override
                        public void unscheduleDrawable(Drawable who, Runnable what) {
                            handler.removeCallbacks(what);
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shader != null) {
            paint.setShader(shader);
        }
        super.onDraw(canvas);
    }
}
