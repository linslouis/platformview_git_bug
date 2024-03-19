package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class AndroidSurfaceView implements PlatformView, SurfaceHolder.Callback {
    private static final String TAG = "AndroidSurfaceView";
    private final SurfaceView surfaceView;
    private final MediaPlayer mediaPlayer;
    private final Context context;
    private int playbackPosition = 0; // To save playback position

    public AndroidSurfaceView(Context context, BinaryMessenger viewId, Object args) {
        this.context = context;
        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(this);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                Log.d(TAG, "MediaPlayer is prepared and start playing.");
                mp.start();
                if (playbackPosition > 0) {
                    mp.seekTo(playbackPosition);
                }
            });
            mediaPlayer.setLooping(true); // Optional: For continuous playback
        } catch (IOException e) {
            Log.e(TAG, "Error setting data source", e);
        }

        surfaceView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                // Handle layout changes, potentially adjusting MediaPlayer or SurfaceView settings
                Log.d(TAG, "SurfaceView layout changed.");
            }
        });

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d(TAG, "Surface created.");
        mediaPlayer.setDisplay(holder);
        if (playbackPosition > 0) {
            mediaPlayer.seekTo(playbackPosition);
            mediaPlayer.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "Surface changed: format=" + format + ", width=" + width + ", height=" + height);
        // This method is called when the surface size is changed.
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d(TAG, "Surface destroyed.");
        if (mediaPlayer.isPlaying()) {
            playbackPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    @Nullable
    @Override
    public View getView() {
        return surfaceView;
    }

    @Override
    public void dispose() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        Log.d(TAG, "MediaPlayer disposed.");
    }
}
