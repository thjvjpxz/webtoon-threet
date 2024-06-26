package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class GifTextView extends View {
    private Paint textPaint;
    private Movie gifMovie;
    private Bitmap imageBitmap;
    private long movieStart;
    private String text = "Hello World";
    private Handler handler = new Handler();
    private Runnable updateRunnable;
    private long frameDelay = 16;
    private boolean isGif = false; // Option to switch between GIF and JPEG

    public GifTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(0xFF000000); // Set default text color to black
    }

    private void startAnimation() {
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, frameDelay);
            }
        };
        handler.post(updateRunnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFFFFFFF);

        if (isGif && gifMovie != null) {
            long now = SystemClock.uptimeMillis();
            if (movieStart == 0) {
                movieStart = now;
            }
            int duration = gifMovie.duration();
            if (duration == 0) {
                duration = 1000;
            }
            int relTime = (int) ((now - movieStart) % duration);

            gifMovie.setTime(relTime);
            Bitmap bitmap = Bitmap.createBitmap(gifMovie.width(), gifMovie.height(), Bitmap.Config.ARGB_8888);
            Canvas movieCanvas = new Canvas(bitmap);
            gifMovie.draw(movieCanvas, 0, 0);
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            textPaint.setShader(shader);
        } else if (!isGif && imageBitmap != null) {
            BitmapShader shader = new BitmapShader(imageBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            textPaint.setShader(shader);
        }

        float x = getWidth() / 2f;
        float y = getHeight() / 2f - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(text, x, y, textPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(updateRunnable);
    }

    public void setGifFromUrl(String url) {
        isGif = true;
        Glide.with(getContext())
                .as(byte[].class)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new CustomTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] resource, Transition<? super byte[]> transition) {
                        gifMovie = Movie.decodeByteArray(resource, 0, resource.length);
                        startAnimation();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    public void setImageFromUrl(String url) {
        isGif = false;
        Glide.with(getContext())
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        imageBitmap = resource;
                        textPaint.setShader(new BitmapShader(imageBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
                        invalidate();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setBoldText(boolean isBold) {
        if (isBold) {
            textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        invalidate();
    }
}
