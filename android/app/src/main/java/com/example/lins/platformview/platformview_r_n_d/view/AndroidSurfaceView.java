package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;

import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class AndroidSurfaceView implements PlatformView, SurfaceHolder.Callback {
    private final ScrollView scrollView;
    private final FrameLayout frameLayout;
    private final SurfaceView surfaceView;
    private final MediaPlayer mediaPlayer;

    private static final String TAG = "VideoSurfaceViewPluginTest";

    public AndroidSurfaceView(Context context, BinaryMessenger viewId, Object args) {
        scrollView = new ScrollView(context);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT));

        frameLayout = new FrameLayout(context);
        scrollView.addView(frameLayout, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        surfaceView = new SurfaceView(context);
        // Set fixed dimensions for SurfaceView (300x250)
        int fixedWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());
        int fixedHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, context.getResources().getDisplayMetrics());
        FrameLayout.LayoutParams surfaceParams = new FrameLayout.LayoutParams(fixedWidth, fixedHeight, Gravity.CENTER);
        frameLayout.addView(surfaceView, surfaceParams);

        surfaceView.getHolder().addCallback(this);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> mp.start());
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            Log.e(TAG, "Error setting data source for mediaPlayer", e);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "Surface changed: format=" + format + ", width=" + width + ", height=" + height);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    @Nullable
    @Override
    public View getView() {
        return scrollView; // Return the ScrollView that wraps the SurfaceView
    }

    @Override
    public void dispose() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
}

